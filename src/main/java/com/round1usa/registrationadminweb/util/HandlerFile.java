package com.round1usa.registrationadminweb.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

public class HandlerFile {
	private String filePath;
	private String oldName;
	private HttpServletResponse resp;
	private byte[] fileByte;
	private static final String CHARSET = "UTF-8";


	public HandlerFile(HttpServletResponse resp, String filePath) {
		this.resp = resp;
		this.filePath = filePath;
		int lastIndex = filePath.lastIndexOf("/");
		this.oldName = filePath.substring(lastIndex+1);
	}

	public byte[] getDownloadFileByte() {
		dowonload();
		return fileByte;
	}

	private void dowonload() {
		try {
			fileByte = FileUtils.readFileToByteArray(new File(filePath));
			resp.setContentType("application/octet-stream");
			resp.setContentLength(fileByte.length);
			resp.setHeader("Content-Disposition",
					"attachment; fileName=\"" + URLEncoder.encode(oldName, CHARSET) + "\";");
			resp.setHeader("Content-Transfer-Encoding", "binary");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//savePdfOnLocal(multipartFile, null, "0001");
	}

}