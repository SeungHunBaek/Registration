package com.round1usa.registration.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class PdfmakeServiceImpl implements PdfmakeService {

	private static final int EXIT_SUCCESS = 0;
	private final Properties props = new Properties();

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RegistrationService registrationService;

	@Override
	public int callPdfmake(Map<String, Object> request, OutputStream output) throws Exception {
		String storeId = (String) request.get("storeId");
		String uuid = UUID.randomUUID().toString();

		String logId = String.format("[%s-%s]", storeId, uuid);

		ClassPathResource res = new ClassPathResource("pdfmake.properties");
		InputStream propInput = res.getInputStream();
		props.load(propInput);
		propInput.close();

		Map<String, Object> requestAfter = changeBirthdayFormat(request);

		File nodejsAppDir = new File(props.getProperty("app.path"));
		File pdfDir       = new File(props.getProperty("pdf.path"));

		if (!pdfDir.exists()) {
			pdfDir.mkdirs();
		}

		LocalDateTime now = LocalDateTime.now();
		String date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String timestamp = now.format(DateTimeFormatter.ofPattern("HHmmssSSS"));

		File pdfDateDir = new File(pdfDir, date);

		if (!pdfDateDir.exists()) {
			pdfDateDir.mkdir();
		}

		File pdfFile = new File(pdfDateDir, timestamp + ".pdf");

		int i = 0;
		while (pdfFile.exists()) {
			pdfFile = new File(pdfDateDir, timestamp + (++i) + ".pdf");
		}

		logger.info("{} : before Node.js Process.", logId);

		Runtime runtime = Runtime.getRuntime();
		Process nodeProcess = runtime.exec("node index", new String[] {}, nodejsAppDir);

		ObjectMapper jsonMapper = new ObjectMapper();
		String jsonStr = jsonMapper.writeValueAsString(requestAfter);

		new IpcRequest(nodeProcess, jsonStr).run();

		int pageLength = new IpcResponse(nodeProcess, pdfFile, output).call();

		if (pageLength == 0) {
			logger.info("{} : createPDF failed. pageLengh is zero.", logId);
		} else {
			logger.info("{} : after Node.js Process. {} bytes written.", logId, pageLength);
		}

		int errorCode = nodeProcess.waitFor();

		if (errorCode != EXIT_SUCCESS) {
			throw new RuntimeException("createPDF failed. Error Code: " + errorCode);
		} else {
			registrationService.insert(request, pdfFile.getCanonicalPath());
		}

		return pageLength;
	}

	private static class IpcRequest implements Runnable {
		private final Process proc;
		private String json;

		private IpcRequest(Process proc, String json) {
			this.proc = proc;
			this.json = json;
		}

		@Override
		public void run() {
			try {
				BufferedOutputStream output = new BufferedOutputStream(proc.getOutputStream());
				IOUtils.write(json, output);
				output.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static class IpcResponse implements Callable<Integer> {
		private final Process proc;
		private final OutputStream fileOutput;
		private final OutputStream pageOutput;

		private static final int BUFFER_SIZE = 1024;
		private static final int F_EOF = -1;

		private final Logger logger = LoggerFactory.getLogger(getClass());

		private IpcResponse(Process proc, File outputPath, OutputStream output) {
			try {
				this.proc = proc;
				this.fileOutput = new BufferedOutputStream(new FileOutputStream(outputPath));
				this.pageOutput = output;
			} catch (IOException e) {
				if (this.fileOutput != null) {
					try {
						this.fileOutput.close();
					} catch (IOException e2) {
						// nothing
					}
				}
				throw new RuntimeException(e);
			}
		}

		@Override
		public Integer call() throws Exception {
			int total = 0;

			try {
				BufferedInputStream input = new BufferedInputStream(proc.getInputStream(), BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];

				int len = 0;
				long time1 = System.currentTimeMillis();
				while ((len = input.read(buffer)) != F_EOF) {
					fileOutput.write(buffer, 0, len);
					pageOutput.write(buffer, 0, len);
					total += len;
					logger.debug("read: {}bytes, total: {}bytes", len, total);
				}
				long time2 = System.currentTimeMillis();
				logger.debug("transfer tooks {} msec.", (time2 - time1));

				fileOutput.flush();
				fileOutput.close();
				pageOutput.flush();
				input.close();

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			return total;
		}
	}

	private Map<String, Object> changeBirthdayFormat(Map<String, Object> original) {

		Map<String, Object> copied = new HashMap<String, Object>(original);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> partipicants = new ArrayList<Map<String, Object>>((List<Map<String, Object>>) original.get("list"));

		SimpleDateFormat dfOriginal = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dfCopied   = new SimpleDateFormat("MM/dd/yyyy");

		for (Map<String, Object> partipicant: partipicants) {
			String birthDay = (String) partipicant.get("birthDay");

			try {
				Date originalDate = dfOriginal.parse(birthDay);
				String birthDayFormatted = dfCopied.format(originalDate);

				partipicant.put("birthDay", birthDayFormatted);
			} catch (Exception e) {
				continue;
			}
		}

		copied.put("list", partipicants);
		return copied;
	}
}
