package com.round1usa.registrationadminweb.control;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.SqlSession;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.round1usa.registrationadminweb.dao.RegistrationDao;
import com.round1usa.registrationadminweb.dto.Agreement;
import com.round1usa.registrationadminweb.dto.StoreDto;
import com.round1usa.registrationadminweb.service.RegistrationService;
import com.round1usa.registrationadminweb.util.HandlerFile;

/**
 * Handles requests for the application home page.
 */
@Controller
public class RegistrationAdminControl {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationAdminControl.class);

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private ServletContext servletContext;

	private ArrayList<StoreDto> list = new ArrayList<StoreDto>();

	private RegistrationService service = new RegistrationService();

	@Value("#{Admin['Admin1.UserID']}")
	private String Admin1User;

	@Value("#{Admin['Admin1.Password']}")
	private String Admin1Password;

	@Value("#{Admin['Admin2.UserID']}")
	private String Admin2User;

	@Value("#{Admin['Admin2.Password']}")
	private String Admin2Password;

	@Value("#{HQ['HQ.UserID']}")
	private String HQUser;




	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(HttpServletRequest request, Model model) {
		logger.info("main");


		HttpSession session = request.getSession(false);
		String nextPage = "";
        if(session == null) {
        	logger.info("not session");
        	nextPage = "login";
        }else{
        	logger.info("session on");
        	nextPage = "redirect:/agreements";
        }

		return nextPage;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		logger.info("login");
		return "login";
	}

	@RequestMapping(value = "/loginPost", method = RequestMethod.POST)
	public String loginPost(HttpServletRequest request, Model model) {
		logger.info("loginPost");

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		RegistrationDao dao =sqlSession.getMapper(RegistrationDao.class);

		StoreDto storeDto = dao.checkStoreId(id);

		String nextPage = "redirect:/";

		if ((id.equals(Admin1User) && pw.equals(Admin1Password))||(id.equals(Admin2User) && pw.equals(Admin2Password))) {
			HttpSession session = request.getSession(true);
			logger.info("admin login success");
			session.setAttribute("login", "admin");
			nextPage = "redirect:agreements";
		} else if(storeDto!=null&&storeDto.getStore_password().equals(pw)){
			logger.info("manager login success");
			HttpSession session = request.getSession(true);
			session.setAttribute("login", storeDto.getStore_id());
			nextPage = "redirect:agreements";
		} /*else{
			logger.info("manager login fail");
			model.addAttribute("msg", "Please check your ID and password");
			model.addAttribute("url", "login");
			return "alert";
		}*/
		logger.info("nextpage: "+nextPage);

		return nextPage;
	}

	@RequestMapping(value = "/alert", method = RequestMethod.GET)
	public String alert( Model model, HttpServletRequest request) {
		logger.info("alert");

		return "redirect:/";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout( Model model, HttpServletRequest request) {
		logger.info("logout");
		HttpSession session = request.getSession(false);
			session.invalidate();


		return "redirect:/";
	}

	@RequestMapping(value = "/store", method = RequestMethod.GET)
	public String store( Model model) {
		logger.info("store");

		RegistrationDao dao =sqlSession.getMapper(RegistrationDao.class);
		list=dao.viewStoreList();
		model.addAttribute("list", list);

		return "store";
	}

	@RequestMapping(value = "/agreements", method = RequestMethod.GET)
	public String agreements( Model model) {
		logger.info("agreements");
		RegistrationDao dao =sqlSession.getMapper(RegistrationDao.class);
		list=dao.viewStoreList();

		Iterator<StoreDto> iter = list.iterator();

		while(iter.hasNext()){
			StoreDto s = iter.next();

			if(s.getStore_id().equals(HQUser)){
				iter.remove();
			}
		}

		model.addAttribute("list", list);

		return "agreements";
	}

	@RequestMapping(value = "/agreements/data.json", method = RequestMethod.GET)
	@ResponseBody
	public String agreementsDataJson(
			HttpServletRequest request,
			@RequestParam(required=false) String     draw,
			@RequestParam(required=false) BigInteger start,
			@RequestParam(required=false) BigInteger length,
			@RequestParam(value="search[value]", required=false) String globalSearchValue,
			@RequestParam(value="columns[1][search][value]", required=false) String storeNameSearchValue
			) throws Exception {
		RegistrationDao dao =sqlSession.getMapper(RegistrationDao.class);

		HttpSession session = request.getSession(false);
		String store_id = (String)session.getAttribute("login");

		if(store_id.equals(HQUser)){
			store_id ="admin";
		}

		String bindContains = "%{0}%";

		HashMap<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("parentName", MessageFormat.format(bindContains, globalSearchValue));
		conditions.put("phone", MessageFormat.format(bindContains, globalSearchValue));
		conditions.put("store_id", store_id);

		logger.info("storeName: {}", storeNameSearchValue);
		if (storeNameSearchValue != null && storeNameSearchValue.length() > 0) {
			conditions.put("storeName", storeNameSearchValue);
		}
		logger.info("conditions: {}", conditions);


		BigInteger totalCount = dao.agreementStoreCount(null);
		BigInteger filteredCount = dao.agreementStoreCount(conditions);
		ArrayList<Agreement> agreementlist = dao.agreementStoreList(conditions, start, length, store_id);

		if(!(store_id.equals(HQUser))){
			conditions.replace("parentName", null);
			conditions.replace("phone", null);
			totalCount = dao.agreementStoreCount(conditions);
		}

		ObjectMapper mapper = new ObjectMapper();

		LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("draw", draw);
		data.put("recordsTotal", totalCount);
		data.put("recordsFiltered", filteredCount);

		String htmlTemplate =
			IOUtils.toString(new FileInputStream(servletContext.getRealPath("/WEB-INF/templates/etc.html")));

		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		for (Agreement a: agreementlist) {
			String pdfPath = a.getPdf_path();
			String pdfLinkPath = pdfPath.replace("C:/", "/").substring(1);

			ArrayList<String> item = new ArrayList<String>();
			item.add(a.getRequest_datetime());
			item.add(a.getStore_name());
			item.add(a.getParent_name());
			item.add(a.getPhone());

			String html = htmlTemplate
					.replace("${pdfPath}", pdfPath)
					.replace("${pdfLinkPath}", pdfLinkPath);

			item.add(html);
			arr.add(item);
		}

		data.put("data", arr);



		return mapper.writeValueAsString(data);
	}

	@RequestMapping(value = "/updateStore", method = RequestMethod.POST)
	public String updateStore(HttpServletRequest request, Model model) {
		logger.info("updateStore");
		logger.info("org_store_id : " + request.getParameter("org_store_id"));
		logger.info("update_store_id : " + request.getParameter("update_store_id"));
		logger.info("update_store_name : " + request.getParameter("update_store_name"));

		String org_store_id = request.getParameter("org_store_id");
		String update_store_id = request.getParameter("update_store_id");
		String update_store_name = request.getParameter("update_store_name");
		String update_store_password = request.getParameter("update_store_password");

		RegistrationDao dao =sqlSession.getMapper(RegistrationDao.class);
		boolean result = false;
		result = service.updateStoreService(dao, org_store_id, update_store_id, update_store_name, update_store_password);
		logger.info("result : " + result);

		return "redirect:store";
	}

	@RequestMapping(value = "/deleteStore", method = RequestMethod.POST)
	public String deleteStore(HttpServletRequest request, Model model) {
		logger.info("deleteStore");

		String store_id = request.getParameter("store_id");
		RegistrationDao dao =sqlSession.getMapper(RegistrationDao.class);
		boolean result = false;
		result = service.deleteStoreService(dao, store_id);
		logger.info("result : " + result);

		return "redirect:store";
	}

	@RequestMapping(value = "/insertStore", method = RequestMethod.POST)
	public String insertStore(HttpServletRequest request, Model model) {
		logger.info("insertStore");
		logger.info("insert_store_id : " + request.getParameter("insert_store_id"));
		logger.info("insert_store_name : " + request.getParameter("insert_store_name"));

		String insert_store_id = request.getParameter("insert_store_id");
		String insert_store_name = request.getParameter("insert_store_name");
		String insert_store_password = request.getParameter("insert_store_password");

		RegistrationDao dao =sqlSession.getMapper(RegistrationDao.class);
		boolean result = false;
		result = service.insertStoreService(dao, insert_store_id, insert_store_name, insert_store_password);
		logger.info("result : " + result);

		return "redirect:store";
	}

	@RequestMapping(value = "/fileDownload")
	@ResponseBody
	public byte[] fileDownload(HttpServletRequest request, HttpServletResponse resp) {
		logger.info("fileDownload");

		String filePath = request.getParameter("PdfPath");

		return new HandlerFile(resp, filePath).getDownloadFileByte();
	}
}
