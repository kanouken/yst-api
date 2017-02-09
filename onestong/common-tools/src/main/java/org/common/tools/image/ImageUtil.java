package org.common.tools.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * 文字水印
 */
public class ImageUtil {
	/**
	 * @param args
	 */
	// public static void main(String[] args) {
	// // 原图
	// String srcImgPath = "/Users/xnq/Documents/伟星新材/质保卡/伟星微信－切图/保修卡@3x.png";
	// // 加了字的图
	// String targerPath = "/Users/xnq/Documents/伟星新材/质保卡/伟星微信－切图/grurtt.png";
	// // 给图片添加字
	// ImageUtil.pressText("NO.329160000001", srcImgPath, targerPath, 201 * 3,
	// 260 * 3, 1f);// 测试OK
	// System.out.println("ok");
	// }

	/*
	 * 给图片添加文字水印
	 * 
	 * @param pressText 水印文字
	 * 
	 * @param srcImageFile 源图像地址
	 * 
	 * @param destImageFile 目标图像地址
	 * 
	 * @param fontName 水印的字体名称
	 * 
	 * @param fontStyle 水印的字体样式
	 * 
	 * @param color 水印的字体颜色
	 * 
	 * @param fontSize 水印的字体大小
	 * 
	 * @param x 修正值
	 * 
	 * @param y 修正值
	 * 
	 * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressText(String pressText, String srcImageFile, String destImageFile, int x, int y,
			float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			// 开文字抗锯齿 去文字毛刺
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.drawImage(src, 0, 0, width, height, null);
			// 设置颜色
			g.setColor(new Color(0, 136, 86));
			// 设置 Font
			g.setFont(new Font("宋体", Font.BOLD, 16 * 3));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			// 第一参数->设置的内容，后面两个参数->文字在图片上的坐标位置(x,y) .
			g.drawString(pressText, x, y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "PNG", new File(destImageFile));// 输出到文件流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}