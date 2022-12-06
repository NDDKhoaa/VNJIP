package vnjip.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vnjip.entity.FileUpload;
import vnjip.model.BaseModel;
import vnjip.services.Impl.FileUploadServiceImpl;

@Controller
public class FileController {

	@Autowired
	private FileUploadServiceImpl fileServiceImpl;

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
	public String saveFile(@ModelAttribute("fileForm") BaseModel model,
			@RequestParam("file") MultipartFile multipartFile) throws IOException {
		String fileFolderName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long fileSize = multipartFile.getSize();
		byte[] content = multipartFile.getBytes();
		model.setFilefolderName(fileFolderName);
		model.setFilesize(fileSize);
		model.setFilecontent(content);
		if ("".equals(model.getFileName())) {
			model.setFileName(fileFolderName);
			FileUpload file = new FileUpload(model);
			fileServiceImpl.save(file);
		} else {
			String fileFolderName1 = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			String[] result = fileFolderName1.split("\\.");
			String newFileName = model.getFileName() + "." + result[1];
			model.setFileName(newFileName);
			FileUpload file = new FileUpload(model);
			fileServiceImpl.save(file);
		}

		return "redirect:/viewFiles";
	}

	@RequestMapping("/modifyFile")
	public ModelAndView modifyFile(@RequestParam("fileNumber") long fileNumber) {
		ModelAndView mav = new ModelAndView("/file/modifyFile");
		FileUpload file = fileServiceImpl.findByNumber(fileNumber);
		mav.addObject("updateFile", file);
		mav.addObject("baseModel", new BaseModel());
		return mav;
	}

	@RequestMapping(value = "/saveFileModify", method = RequestMethod.POST)
	public String saveFileModify(@ModelAttribute("updateFile") FileUpload updateFile,
			@ModelAttribute("fileForm") BaseModel model, @RequestParam("fileNumber") long fileNumber,
			@RequestParam("file") MultipartFile multipartFile) throws IOException {
		FileUpload fileId = fileServiceImpl.findByNumber(fileNumber);
		String fileFolderName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long fileSize = multipartFile.getSize();
		byte[] content = multipartFile.getBytes();
		model.setFilefolderName(fileFolderName);
		model.setFilesize(fileSize);
		model.setFilecontent(content);
		if (model.getFileName() != null) {
			String fileFolderName1 = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			String[] result = fileFolderName1.split(".");
			String newFileName = model.getFileName() + result[1];
			model.setFileName(newFileName);
			FileUpload file = new FileUpload(model);
			file.setFileNumber(fileId.getFileNumber());
			fileServiceImpl.save(file);
		} else {
			model.setFileName(fileFolderName);
			FileUpload file = new FileUpload(model);
			file.setFileNumber(fileId.getFileNumber());
			fileServiceImpl.save(file);
		}
		return "redirect:/viewFiles";
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
}
