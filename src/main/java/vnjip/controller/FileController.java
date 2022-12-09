package vnjip.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vnjip.entity.FileUpload;
import vnjip.model.BaseModel;
import vnjip.services.Impl.ErrorPfImpl;
import vnjip.services.Impl.FileUploadServiceImpl;

@Controller
public class FileController {

	@Autowired
	private FileUploadServiceImpl fileServiceImpl;

	@Autowired
	private ErrorPfImpl errorPfImpl;

	@RequestMapping("/viewFiles")
	public String viewFile(Model model) {
		List<FileUpload> listFile = fileServiceImpl.listAll();
		model.addAttribute("listFile", listFile);
		return "/file/viewFiles";
	}

	@RequestMapping(value = "/viewFileDetails", method = RequestMethod.GET)
	public ModelAndView viewFileDetails(@RequestParam(value = "fileNumber") long fileNumber) {
		ModelAndView mav = new ModelAndView("/file/viewFileDetails");
		FileUpload file = fileServiceImpl.findByNumber(fileNumber);
		BaseModel baseModel = new BaseModel(file);
		mav.addObject("baseModel", baseModel);
		return mav;
	}

	@RequestMapping("/createFile")
	public String createFile(Model model) {
		BaseModel baseModel = new BaseModel();
		FileUpload file = fileServiceImpl.findTopFileNumber();
		if (file != null) {
			baseModel.setFileNumber(file.getFileNumber() + 1);
		} else {
			long id = 1;
			baseModel.setFileNumber(id);
		}
		model.addAttribute("fileForm", baseModel);
		return "/file/createFile";
	}

	@RequestMapping(value = "/saveFile", method = RequestMethod.POST)
	public ModelAndView saveFile(@ModelAttribute("fileForm") BaseModel model,
			@RequestParam("file") MultipartFile multipartFile) throws IOException {
		FileUpload file = fileServiceImpl.findTopFileNumber();
		String fileFolderName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long fileSize = multipartFile.getSize();
		byte[] content = multipartFile.getBytes();
		model.setFilefolderName(fileFolderName);
		model.setFilesize(fileSize);
		model.setFilecontent(content);
		if (file != null) {
			model.setFileNumber(file.getFileNumber() + 1);
		} else {
			long id = 1;
			model.setFileNumber(id);
		}
		List<String> errorList = new ArrayList<>();
		validationNotNull(model, errorList);
		if (errorList.size() == 0) {
			validationType(model, errorList);
		}
		if (errorList.size() == 0) {
			validate2010(model, errorList);
		}
		if (errorList.size() > 0) {
			ModelAndView mav = new ModelAndView("/file/createFile");

			model.setErrorList(errorList);
			mav.addObject("modelList", errorList);
			mav.addObject("fileForm", model);
			System.out.println(errorList.size());
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("redirect:/viewFiles");
			ModelAndView mav2 = new ModelAndView("/file/createFile");
			model.setFilefolderName(fileFolderName);
			model.setFilesize(fileSize);
			model.setFilecontent(content);
			if ("".equals(model.getFileName())) {
				model.setFileName(fileFolderName);
				validate2010(model, errorList);
				if (errorList.size() > 0) {
					return mav2;
				} else {
					FileUpload file2 = new FileUpload(model);
					fileServiceImpl.save(file2);
				}
			} else {
				String fileFolderName1 = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				String[] result = fileFolderName1.split("\\.");
				String newFileName = model.getFileName() + "." + result[1];
				model.setFileName(newFileName);
				FileUpload file2 = new FileUpload(model);
				fileServiceImpl.save(file2);
			}
			return mav;

		}
	}

	@RequestMapping("/modifyFile")
	public ModelAndView modifyFile(@RequestParam("fileNumber") long fileNumber) {
		ModelAndView mav = new ModelAndView("/file/modifyFile");
		FileUpload file = fileServiceImpl.findByNumber(fileNumber);
		BaseModel model = new BaseModel(file);
		mav.addObject("updateFile", file);
		mav.addObject("baseModel", model);
		return mav;
	}

	@RequestMapping(value = "/saveFileModify", method = RequestMethod.POST)
	public ModelAndView saveFileModify(@ModelAttribute("updateFile") FileUpload updateFile,
			@ModelAttribute("fileForm") BaseModel md, @RequestParam("fileNumber") long fileNumber,
			@RequestParam("file") MultipartFile multipartFile) throws IOException {

		BaseModel model = new BaseModel(updateFile);
		List<String> errorList = new ArrayList<>();
		validationNotNullModify(model, errorList);
		if (errorList.size() == 0) {
			validationType(model, errorList);
		}
		if (errorList.size() == 0) {
			validate2011(model, errorList);
		}

		if (errorList.size() > 0) {
			ModelAndView mav = new ModelAndView("/file/modifyFile");
			mav.addObject("modelList", errorList);
			mav.addObject("updateFile", updateFile);
			mav.addObject("baseModel", model);
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("redirect:/viewFiles");
			if (multipartFile.getSize() != 0) {
				FileUpload fileId = fileServiceImpl.findByNumber(fileNumber);
				String fileFolderName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				long fileSize = multipartFile.getSize();
				byte[] content = multipartFile.getBytes();
				model.setFilefolderName(fileFolderName);
				model.setFilesize(fileSize);
				model.setFilecontent(content);
				model.setFileDateUpload(fileId.getDateUpload());
				System.out.println(fileId.getFileName());
				System.out.println(model.getFileName());
				if ("".equals(model.getFileName())) {
					model.setFileName(fileFolderName);
					FileUpload file2 = new FileUpload(model);
					file2.setFileNumber(fileId.getFileNumber());
					fileServiceImpl.save(file2);
				} else {
					if (model.getFileName().equals(fileFolderName)) {
						model.setFileName(fileFolderName);
						FileUpload file2 = new FileUpload(model);
						file2.setFileNumber(fileId.getFileNumber());
						fileServiceImpl.save(file2);
					} else {
						String[] result2 = model.getFileName().split("\\.");
						String[] result = fileFolderName.split("\\.");
						String newFileName = result2[0] + "." + result[1];
						model.setFileName(newFileName);
						FileUpload file2 = new FileUpload(model);
						file2.setFileNumber(fileId.getFileNumber());
						fileServiceImpl.save(file2);
					}
				}
			} else {
				FileUpload fileId = fileServiceImpl.findByNumber(fileNumber);
				model.setFilefolderName(fileId.getFileName());
				model.setFilesize(fileId.getSize());
				model.setFilecontent(fileId.getContent());
				model.setFileDateUpload(fileId.getDateUpload());
				if ("".equals(model.getFileName())) {
					model.setFileName(fileId.getFileName());
					FileUpload file2 = new FileUpload(model);
					file2.setFileNumber(fileId.getFileNumber());
					fileServiceImpl.save(file2);
				} else {
					if (model.getFileName().equals(fileId.getFileName())) {
						model.setFileName(fileId.getFileName());
						FileUpload file2 = new FileUpload(model);
						file2.setFileNumber(fileId.getFileNumber());
						fileServiceImpl.save(file2);
					} else {
						String[] result2 = model.getFileName().split("\\.");
						String[] result = fileId.getFolderName().split("\\.");
						String newFileName = result2[0] + "." + result[1];
						model.setFileName(newFileName);
						FileUpload file2 = new FileUpload(model);
						file2.setFileNumber(fileId.getFileNumber());
						fileServiceImpl.save(file2);
					}
				}
			}
			List<FileUpload> listFile = fileServiceImpl.listAll();
			mav.addObject("listFile", listFile);
			return mav;
		}
	}

	@RequestMapping("/searchFile")
	public ModelAndView searchFile(@RequestParam String word) {
		ModelAndView mav = new ModelAndView("/file/searchFileResults");
		List<BaseModel> modelList = new ArrayList<BaseModel>();
		List<FileUpload> fileList = fileServiceImpl.search(word);
		if (!fileList.isEmpty()) {
			for (FileUpload file : fileList) {
				BaseModel model = new BaseModel(file);
				modelList.add(model);
			}
		}
		mav.addObject("modelList", modelList);
		return mav;
	}

	@GetMapping("/downloadFile")
	public void downloadFile(@Param("fileNumber") long fileNumber, HttpServletResponse response) throws IOException {
		FileUpload file = fileServiceImpl.findByNumber(fileNumber);
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + file.getFileName();
		response.setHeader(headerKey, headerValue);
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(file.getContent());
		outputStream.close();
	}

	@RequestMapping("/deleteFile")
	public String deleteFile(@RequestParam("fileNumber") long fileNumber) {
		fileServiceImpl.deleteByNumber(fileNumber);
		return "redirect:/viewFiles";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}

	@RequestMapping(value = "/file-multi-delete", method = RequestMethod.POST)
	public String deleteFiles(@RequestParam long[] ids, Model model) {
		for (long l : ids) {
			if (ids.length > 0) {
				fileServiceImpl.deleteByNumber(l);
			}
		}
		return "redirect:/viewPolicies";
	}

	public void validationNotNull(BaseModel baseModel, List<String> errorList) {
		if (baseModel.getFilesize() == 0) {
			baseModel.setErrorCode("E186");
			errorList.add("File Upload " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getFilesize() > 20000000) {
			baseModel.setErrorCode("E195");
			errorList.add("File Upload " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getFileDateUpload() == null) {
			baseModel.setErrorCode("E186");
			errorList.add("Agent DOB " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
	}

	public void validationNotNullModify(BaseModel baseModel, List<String> errorList) {
		if (baseModel.getFileDateUpload() == null) {
			baseModel.setErrorCode("E186");
			errorList.add("Agent DOB " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (baseModel.getFilesize() > 20000000) {
			baseModel.setErrorCode("E195");
			errorList.add("File Upload " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
	}

	public void validationType(BaseModel baseModel, List<String> errorList) {
		if (isNumeric(baseModel.getFileName().trim())) {
			baseModel.setErrorCode("E189");
			errorList.add("File Name " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());

		}
	}

	public void validate2010(BaseModel baseModel, List<String> errorList) {
		if (fileServiceImpl.findByFileName((baseModel.getFileName()))) {
			baseModel.setErrorCode("E190");
			errorList.add("File Name " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (daysBetween2Dates(baseModel.getFileDateUpload()) <= 0) {
			baseModel.setErrorCode("E192");
			errorList.add("Date Upload " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
	}

	public void validate2011(BaseModel baseModel, List<String> errorList) {
		if (fileServiceImpl.findByFileNameModify((baseModel.getFileName()))) {
			baseModel.setErrorCode("E190");
			errorList.add("File Name " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
		if (daysBetween2Dates(baseModel.getFileDateUpload()) <= 0) {
			baseModel.setErrorCode("E192");
			errorList.add("Date Upload " + errorPfImpl.findByShort(baseModel.getErrorCode()).getErrorDesc());
		}
	}

	public boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public boolean checkSpecial(String str) {
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
		Matcher matcher = pattern.matcher(str);
		boolean isStringContainsSpecialCharacter = matcher.find();
		if (isStringContainsSpecialCharacter)
			return true;
		else
			return false;
	}

	public long daysBetween2Dates(Date date1) {

		Date date2 = new Date(System.currentTimeMillis());
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);

		long noDay = (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);
		return noDay;

	}
}
