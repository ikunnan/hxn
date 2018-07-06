package com.hxn;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class Qrcode2作业 {
	public static void creatCode(String content, String imgPath, int version, String startRgb, String endRgb) throws IOException{
		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeVersion(version);
		//默认版本号3
        //System.out.println("版本号： "+qrcode.getQrcodeVersion());
		//排错率，L(7%)  Q(25%)  H(30%)
        //拍错率越高，可存储信息就越少
		qrcode.setQrcodeErrorCorrect('Q');
		//注意版本消息N代表数字，A代表a-在，A-Z，B代表任意字符
		qrcode.setQrcodeEncodeMode('B');
		System.out.println("可存储类型："+qrcode.getQrcodeEncodeMode());
		int imgSize = 67 + (version - 1)*12;	
		//生成二维码缓存对象
		BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_BGR);
		//创建绘画对象
		Graphics2D gs = bufferedImage.createGraphics();
		//设置背景
		gs.setBackground(Color.WHITE);
		//设置填充颜色
		gs.setColor(Color.BLACK);
		//清空面板
		gs.clearRect(0, 0, imgSize, imgSize);
		//二维码内容
		//通过二维码要填充的内容获取一个布尔型的二维数组
		boolean [][] calQrcode = qrcode.calQrcode(content.getBytes());
		//生成偏移量
		int pixoff = 2;
		int startR = 0,startG = 0,startB = 0;
		int endR = 0,endG = 0,endB = 0;
		if (null != endRgb) {
			String [] rgb = startRgb.split(",");
			startR = Integer.valueOf(rgb[0]);
			startG = Integer.valueOf(rgb[1]);
			startB = Integer.valueOf(rgb[2]);
		}
		if (null != startRgb) {
			String [] rgb = endRgb.split(",");
			endR = Integer.valueOf(rgb[0]);
			endG = Integer.valueOf(rgb[1]);
			endB = Integer.valueOf(rgb[2]);
		}
		Random random = new Random();
		for (int i = 0; i < calQrcode.length; i++) {//遍历行
			System.out.println("行数:  "+calQrcode.length+ "   列数：  "+ calQrcode[i].length);
			for (int j = 0; j < calQrcode.length; j++) {//遍历列
				if (calQrcode [i][j]) {
					int r = startR + (endR - startR)*(j+1)/calQrcode.length;
					int g = startG + (endG - startG)*(j+1)/calQrcode.length;
					int b = startB + (endB - startB)*(j+1)/calQrcode.length;
					Color color = new Color(r,g,b);
					gs.setColor(color);
					gs.fillRect(i*3+pixoff, j*3+pixoff,3,3);
				}
			}
		}
		//添加头像
		
		BufferedImage logo = ImageIO.read(new File("1.jpg"));
		//头像大小
		int logoSize = imgSize/3;
		//头像起始位置
		int o = (imgSize - logoSize)/2;
		//往二维码上画头像
		gs.drawImage(logo, o, o, logoSize,logoSize, null);
		
		
		//关闭绘画对象
		gs.dispose();
		//把缓冲区图片输出到内存中
		bufferedImage.flush();
		//把缓冲区图片输出到硬盘中
		try {
			ImageIO.write(bufferedImage, "png", new File("qr.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        System.out.println("ok");
	}

	public static void main(String[] args) throws IOException {
		String imgPath = "qrcode.png"; 
		int version = 15;
		String content = "BEGIN:VCARD\r\n" + 
		   "FN:姓名:黄曦楠\r\n"+
		   "TITLE:河北科技师范学院\r\n" + 
		   "TEL;CELL;VOICE:15533516365\r\n"+
		   "URL:http://www.baidu.com\r\n"+
		   "END:VCARD";
		String startRgb = "255,0,0";
		String endRgb = "0,0,255";
		creatCode(content,imgPath,version,startRgb,endRgb);
		
	}

}
