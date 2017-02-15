package com.zhy.demo.mynews.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;


import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

/**
 * Created Time: 2017/2/15.
 * Author:  zhy
 * 功能：打印工具类
 */

public class PrinterUtils {

    private Charset charset;

    private String bluetoothMacAddress;

    private BluetoothSocket bluetoothSocket;

    private InputStream input;

    private OutputStream output;

    private Vector<Byte> outputBuffer = new Vector<>();

    public PrinterUtils() {
    }

    /**
     * 蓝牙连接
     *
     * @param macAddress 地址
     * @return
     */
    public boolean bluetoothConnect(String macAddress) {
        this.bluetoothMacAddress = macAddress;
        try {
            BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = btAdapter.getRemoteDevice(bluetoothMacAddress);
            bluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            bluetoothSocket.connect();
            input = bluetoothSocket.getInputStream();
            output = bluetoothSocket.getOutputStream();
            return true;
        } catch (Exception e) {
            Log.e(PrinterUtils.class.getName(), "通过蓝牙连接打印机失败", e);
            return false;
        }
    }

    /**
     * 测试连接 ,先判断是否有MAC地址，
     *
     * @return
     */
    public boolean testConnect() {
        if (bluetoothSocket == null || output == null
                || !bluetoothSocket.isConnected()
                || !sendCommand(new byte[]{0x00})) {
            return false;
        }
        return true;
    }

    /**
     * 关闭蓝牙连接
     *
     * @return
     */
    public boolean close() {
        try {
            if (bluetoothSocket != null && bluetoothSocket.isConnected()) {
                bluetoothSocket.close();
            }
            input = null;
            output = null;
            outputBuffer = new Vector<>();
            bluetoothSocket = null;
            return true;
        } catch (Exception e) {
            Log.e(PrinterUtils.class.getName(), "关闭蓝牙打印机连接失败", e);
            return false;
        }

    }

    /**
     * 直接发送指令<p>
     * 不建议直接调用
     *
     * @param command 指令
     * @return
     */
    public boolean sendCommand(byte[] command) {
        try {
            output.write(command);
            return true;
        } catch (Exception e) {
            Log.e(PrinterUtils.class.getName(), "直接发送ESC/POS打印机指令失败", e);
            return false;
        }
    }

    /**
     * 设置字符集
     *
     * @param charset 字符集
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * 重置打印设置<p>
     * 字体、字大小
     *
     * @return
     */
    public void escpos_reset() {
        escpos_setFontA();
        escpos_setBold(false);
        escpos_setSizeX0();
    }

    /**
     * 设置字体A(12 × 24)<p>
     * ESC M 0x00
     *
     * @return
     */
    public void escpos_setFontA() {
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1B, 0x4D, 0x00}));
    }

    /**
     * 设置字体B(9 × 17)<p>
     * ESC M 0x01
     *
     * @return
     */
    public void escpos_setFontB() {
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1B, 0x4D, 0x01}));
    }

    /**
     * 设置字体加粗<p>
     * ESC E 0x00(0x01)
     *
     * @param bold 是否加粗
     * @return
     */
    public void escpos_setBold(boolean bold) {
        byte b = 0x00;
        if (bold) {
            b = 0x01;
        }
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1B, 0x45, b}));
    }

    /**
     * 放大零倍字符<p>
     * GS ! 0x00
     *
     * @return
     */
    public void escpos_setSizeX0() {
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1D, 0x21, 0x00}));
    }

    /**
     * 放大一倍字符<p>
     * GS ! 0x11
     *
     * @return
     */
    public void escpos_setSizeX1() {
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1D, 0x21, 0x11}));
    }

    /**
     * 放大一倍字符<p>
     * GS ! 0x11
     *
     * @return
     */
    public void escpos_setSizeX1Half() {
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1D, 0x21, 0x19}));
    }

    /**
     * 放大两倍字符<p>
     * GS ! 0x22
     *
     * @return
     */
    public void escpos_setSizeX2() {
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1D, 0x21, 0x22}));
    }

    /**
     * 设置行距<p>
     * ESC 3 n
     *
     * @param n 倍数（十进制）
     * @return
     */
    public void escpos_setLineSpacing(byte n) {
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1D, 0x50, 0, 20}));
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1B, 0x33, n}));
    }

    /**
     * 设置行距<p>
     * ESC 3 n<p>
     * 一毫米
     *
     * @param n 倍数（十进制）
     * @return
     */
    public void escpos_setLineSpacing_1mm(byte n) {
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1D, 0x50, 0, 25}));
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1B, 0x33, n}));
    }

    /**
     * 设置左边距<p>
     *
     * @param n 间距
     * @return
     */
    public void escpos_setMarginLeft(byte n) {
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1D, 0x50, 5, 20}));
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1D, 0X4C, n, 0x00}));
    }

    /**
     * 设置 居中
     *
     * @param n 0左 1中 2右
     */
    public void escpos_setCentre(byte n) {
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1B, 0x61, n}));
    }

    /**
     * 打印文本<p>
     * "" 0x0A(LF)
     *
     * @param text 文本
     * @return
     */
    public void escpos_printText(String text) {
        for (byte b : text.getBytes(charset != null ? charset : Charset.defaultCharset())) {
            outputBuffer.add(b);
        }
    }

    /**
     * 打印文本<p>
     * "" 0x0A(LF)
     *
     * @param text 文本
     * @return
     */
    public void escpos_printTextLF(String text) {
        for (byte b : text.getBytes(charset != null ? charset : Charset.defaultCharset())) {
            outputBuffer.add(b);
        }
        outputBuffer.add((byte) 0x0A);
    }

    /**
     * 打印文本不换行
     *
     * @param text       文本
     * @param font       字体（1、2）
     * @param bold       是否加粗
     * @param size       放大(0~2)
     * @param marginLeft 左边距
     * @return
     */
    public void escpos_printTextAndFontAndBoldAndSizeAndMarginLeft(String text, int font, boolean bold, int size, int marginLeft) {
        if (font == 1) {
            escpos_setFontA();
        } else if (font == 2) {
            escpos_setFontB();
        }
        escpos_setBold(bold);
        if (size == 0) {
            escpos_setSizeX0();
        } else if (size == 1) {
            escpos_setSizeX1();
        } else if (size == 3) {
            escpos_setSizeX1Half();
        } else if (size == 2) {
            escpos_setSizeX2();
        }
        escpos_setMarginLeft((byte) marginLeft);
        escpos_printText(text);
    }

    /**
     * 打印文本并换行
     *
     * @param text       文本
     * @param font       字体（1、2）
     * @param bold       是否加粗
     * @param size       放大(0~2)
     * @param marginLeft 左边距
     * @return
     */
    public void escpos_printTextAndFontAndBoldAndSizeAndMarginLeftLF(String text, int font, boolean bold, int size, int marginLeft) {
        if (font == 1) {
            escpos_setFontA();
        } else if (font == 2) {
            escpos_setFontB();
        }
        escpos_setBold(bold);
        if (size == 0) {
            escpos_setSizeX0();
        } else if (size == 1) {
            escpos_setSizeX1();
        } else if (size == 2) {
            escpos_setSizeX2();
        }
        escpos_setMarginLeft((byte) marginLeft);
        escpos_printTextLF(text);
    }

    public void escpos_printTextAndFontAndBoldAndSizeAndMarginLeftLFCentre(String text, int font, boolean bold, int size, int centre) {
        if (font == 1) {
            escpos_setFontA();
        } else if (font == 2) {
            escpos_setFontB();
        }
        escpos_setBold(bold);
        if (size == 0) {
            escpos_setSizeX0();
        } else if (size == 1) {
            escpos_setSizeX1();
        } else if (size == 2) {
            escpos_setSizeX2();
        }
        escpos_setCentre((byte) centre);
        escpos_printTextLF(text);
    }

    /**
     * 打印文本并换行
     *
     * @param text       文本
     * @param font       字体（1、2）
     * @param bold       是否加粗
     * @param size       放大(0~2)
     * @param marginLeft 左边距
     * @param marginTop  上边距
     * @return
     */
    public void escpos_printTextAndFontAndBoldAndSizeAndMarginLeftLFAndTop(String text, int font, boolean bold, int size, int marginLeft, int marginTop) {
        if (font == 1) {
            escpos_setFontA();
        } else if (font == 2) {
            escpos_setFontB();
        }
        escpos_setBold(bold);
        if (size == 0) {
            escpos_setSizeX0();
        } else if (size == 1) {
            escpos_setSizeX1();
        } else if (size == 2) {
            escpos_setSizeX2();
        }

        escpos_setMarginLeft((byte) marginLeft);
        escpos_setLineSpacing_1mm((byte) marginTop);
        escpos_printTextLF(text);
    }


    /**
     * 打印二维码
     *
     * @param qrcode     二维码值
     * @param width      宽(px)
     * @param height     高(px)
     * @param marginLeft 左边距
     * @return
     */
    public boolean escpos_printQrcode(String qrcode, int width, int height, int marginLeft) {
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1B, 0x33, 0}));
        //通过zxing产生二维码
        Bitmap bitmap = null;
//        try {
//            Map hints = new Hashtable();
//            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);//纠错能力低（7%）
//            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//            QRCodeWriter qrCodeWriter = new QRCodeWriter();
//            BitMatrix bitMatrix = qrCodeWriter.encode(qrcode, BarcodeFormat.QR_CODE, width, height, hints);
//            //产生位图
//            int w = bitMatrix.getWidth();
//            int h = bitMatrix.getHeight();
//            int[] data = new int[w * h];
//            for (int y = 0; y < h; y++) {
//                for (int x = 0; x < w; x++) {
//                    if (bitMatrix.get(x, y))
//                        data[y * w + x] = 0xff000000;// 黑色
//                    else
//                        data[y * w + x] = -1;// -1 相当于0xffffffff 白色
//                }
//            }
//            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//            bitmap.setPixels(data, 0, w, 0, 0, w, h);
//        } catch (Exception e) {
//            Log.e(PrinterUtils.class.getName(), "产生二维码位图失败", e);
//            return false;
//        }
        escpos_setMarginLeft((byte) marginLeft);
        // 逐行打印
        for (int j = 0; j < bitmap.getHeight() / 24f; j++) {
            byte[] data = new byte[bitmap.getWidth() * 3 + 10];
            int k = 0;
            //打印图片的指令
            data[k++] = 0x1B;
            data[k++] = 0x2A;
            data[k++] = 33;
            data[k++] = (byte) (bitmap.getWidth() % 256); //nL
            data[k++] = (byte) (bitmap.getWidth() / 256); //nH
            //对于每一行，逐列打印
            for (int i = 0; i < bitmap.getWidth(); i++) {
                //每一列24个像素点，分为3个字节存储
                for (int m = 0; m < 3; m++) {
                    //每个字节表示8个像素点，0表示白色，1表示黑色
                    for (int n = 0; n < 8; n++) {
                        if (j * 24 + m * 8 + n < bitmap.getHeight()) {
                            if (bitmap.getPixel(i, j * 24 + m * 8 + n) == -1) {
                                data[k] += data[k] + (byte) 0;
                            } else {
                                data[k] += data[k] + (byte) 1;
                            }
                        } else {
                            data[k] += data[k] + (byte) 0;
                        }

                    }
                    k++;
                }
            }
            data[k++] = 10;//换行
            for (Byte b : data) {
                outputBuffer.add(b);
            }
        }
        return true;
    }

    /**
     * 打印二维码
     *
     * @param qrcode     二维码值
     * @param zoomScale  放大倍数
     * @param marginLeft 左边距
     * @return
     */
    public boolean escpos_printQrcodeByCode(String qrcode, int zoomScale, int marginLeft) {
        if (TextUtils.isEmpty(qrcode)) {
            qrcode = "";
        }
        if (zoomScale > 0) {
            zoomScale += 5;
        } else {
            zoomScale = 5;
        }
        int length = qrcode.length() + 3;
        byte pL = (byte) (length % 256);
        //设置二维码模式
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1D, 0x28, 0x6B, 0x04, 0x00, 0x31, 0x41, 0x32, 0x00}));
        //设置二维码纠错等级
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1D, 0x28, 0x6B, 0x03, 0x00, 0x31, 0x45, 0x31}));
        //设置二维码大小
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1d, 0x28, 0x6b, 0x03, 0x00, 0x31, 0x43, (byte) zoomScale}));
        //设置左边距
        escpos_setMarginLeft((byte) marginLeft);
        //将 信息 存在打印机中
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1d, 0x28, 0x6b, pL, 0x00, 0x31, 0x50, 0x30}));
        byte[] text = qrcode.getBytes();
        int size = text.length;
        for (int i = 0; i < size; i++) {
            outputBuffer.add(text[i]);
        }
        //打印 QRCode
        outputBuffer.addAll(Arrays.asList(new Byte[]{0x1d, 0x28, 0x6b, 0x03, 0x00, 0x31, 0x51, 0x30}));

        return true;

    }


    /**
     * 发送缓存区命令
     *
     * @return
     */
    public boolean escpos_sendBufferCommand() {
        Byte[] Bytes = outputBuffer.toArray(new Byte[0]);
        outputBuffer.clear();
        byte[] bytes = new byte[Bytes.length];
        for (int i = 0; i < Bytes.length; i++) {
            bytes[i] = Bytes[i];
        }
        return sendCommand(bytes);
    }

    /**
     * 切纸（即时发送）
     *
     * @return
     */
    public boolean escpos_cutPaper() {
        //return sendCommand(new byte[]{0x1D, 0x56, 0x41, 0x14});
//        return sendCommand(new byte[]{0x1D, 0x56,0x00, 0x00});
        return sendCommand(new byte[]{0x1B, 0x6D});
    }

    /**
     * 标签机设置<p>
     * 默认开启自动送纸功能
     *
     * @param width           宽度mm
     * @param height          高度mm
     * @param speed           打印速度（2-6）
     * @param density         打印浓度（0-15）
     * @param sensor          标签纸类型（0间隙标签，1黑标标签）
     * @param sensor_distance 标签间间隙高度mm
     * @param sensor_offset   标签间间隙高度补偿mm
     * @return
     */
    public boolean tspl_reset(int width, int height, int speed, int density, int sensor, int sensor_distance, int sensor_offset) {
        tspl_clear();
        String message = "";
        String tearOn = "SET TEAR ON\n";
        String size = "SIZE " + width + " mm" + ", " + height + " mm\n";
        String speed_value = "SPEED " + speed + "\n";
        String density_value = "DENSITY " + density + "\n";
        String sensor_value = "";
        if (sensor == 0) {
            sensor_value = "GAP " + sensor_distance + " mm" + ", " + sensor_offset + " mm\n";
        } else if (sensor == 1) {
            sensor_value = "BLINE " + sensor_distance + " mm" + ", " + sensor_offset + " mm\n";
        }
        message = tearOn + size + speed_value + density_value + sensor_value;
        return sendCommand(message.getBytes());
    }

    /**
     * 走纸
     *
     * @return
     */
    public boolean tspl_formfeed() {
        return sendCommand("FORMFEED\n".getBytes());
    }

    /**
     * 清除数据缓存
     *
     * @return
     */
    public boolean tspl_clear() {
        return sendCommand("CLS\n".getBytes());
    }

    /**
     * 添加文本到缓冲区
     *
     * @param x    x坐标
     * @param y    y坐标
     * @param size 放大倍数
     * @param text 文本
     * @return
     */
    public boolean tspl_printText(int x, int y, int size, String text) {
        return sendCommand(("TEXT " + x + "," + y + ",\"TSS24.BF2\",0," + size + "," + size + ",\"" + text.replace("\"", "'") + "\"\n").getBytes(charset));
    }

    /**
     * 添加文本到缓冲区EN
     *
     * @param x    x坐标
     * @param y    y坐标
     * @param size 放大倍数
     * @param text 文本
     * @return
     */
    public boolean tspl_printTextEn(int x, int y, int size, String text) {
        return sendCommand(("TEXT " + x + "," + y + ",\"3\",0," + size + "," + size + ",\"" + text.replace("\"", "'") + "\"\n").getBytes(charset));
    }

    /**
     * 产生二维码
     *
     * @param x      x坐标
     * @param y      y坐标
     * @param qrcode 文本
     * @return
     */
    public boolean tspl_qrcode(int x, int y, String qrcode) {
        return sendCommand(("QRCODE " + x + "," + y + ",L,5,A,0,\"" + qrcode + "\"\n").getBytes(charset));
    }

    /**
     * 打印缓冲区
     *
     * @return
     */
    public boolean tspl_print() {
        return sendCommand("PRINT 1,1\n".getBytes());
    }

}
