package com.ambition.business.upload.controller;

import com.ambition.common.annotations.AddSysLog;
import com.ambition.common.constants.Constants;
import com.ambition.common.keygen.CommonKeyGenerator;
import com.ambition.common.util.R;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class UploadController {

	private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);


	@Value("${base.upload.path}")
	private String baseFolderPath;

	@Value("${base.http.path}")
	private String baseHttpPath;

	private BufferedImage bufferedImage = null;

	public BufferedImage getLogoImage() {
		if (bufferedImage == null) {
			synchronized (this) {
				if (bufferedImage == null) {
					try {
						bufferedImage = ImageIO.read(new FileInputStream(new File(baseFolderPath, "logo.png")));
					} catch (IOException e) {
						LOG.error("", e);
					}
				}
			}
		}
		return bufferedImage;
	}

	@PostMapping("/upload")
	@RequiresAuthentication
	@AddSysLog
	public R upload(HttpServletRequest request, MultipartFile file) {
		R r = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		StringBuffer url = new StringBuffer();
		String filePath = sdf.format(new Date());
		if (!baseFolderPath.endsWith(File.separator)){
			baseFolderPath += File.separator;
		}
		File baseFolder = new File(baseFolderPath + filePath);
		if (!baseFolder.exists()) {
			baseFolder.mkdirs();
		}
		url.append(baseHttpPath)
				.append("/")
				.append(Constants.IMG_URL_SPLIT)
				.append("/")
				.append(filePath);
		String fileExt = "png";
		String originalFilename = file.getOriginalFilename();
		if (StringUtils.isNotBlank(originalFilename)) {
			fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
		}
		String imgName = CommonKeyGenerator.INSTANCE.generateKey() + "." + fileExt;
		try {
			File dest = new File(baseFolder, imgName);
			file.transferTo(dest);
			Thumbnails.of(dest).size(600, 600).watermark(Positions.BOTTOM_RIGHT, getLogoImage(), 0.5f).toFile(dest);

			url.append("/").append(imgName);

			r = R.ok();
			r.setData(url);

		} catch (IOException e) {
			LOG.error("文件上传错误 , uri: {} , caused by: ", request.getRequestURI(), e);
			r = R.error();
		}

		return r;
	}
}
