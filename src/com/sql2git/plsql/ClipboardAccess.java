package com.sql2git.plsql;

import com.sql2git.Common;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

import java.awt.*;
import java.awt.datatransfer.*;

/**
 * Created by Alex on 28.10.2015.
 */
public class ClipboardAccess extends Common implements ClipboardOwner  {

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    }

    void controlC() {
        User32Ex.INSTANCE.keybd_event((byte) 0x11 /* VK_CONTROL*/, (byte) 0, 0, 0);
        User32Ex.INSTANCE.keybd_event((byte) 0x43 /* 'C' */, (byte) 0, 0, 0);
        User32Ex.INSTANCE.keybd_event((byte) 0x43 /* 'C' */, (byte) 0, 2 /* KEYEVENTF_KEYUP */, 0);
        User32Ex.INSTANCE.keybd_event((byte) 0x11 /* VK_CONTROL*/, (byte) 0, 2 /* KEYEVENTF_KEYUP */, 0);// 'Left Control Up
    }

    String getClipboardText() {
        try{
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch(UnsupportedFlavorException e){
            logError("UnsupportedFlavorException: error while transferring data");
            return "";
        } catch (Exception e) {
            logError("Ошибка при чтении из буфера обмена!");
            return "";
        }
    }
    void setClipboardText(String data) {
        try{
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(data), this);
        } catch (IllegalStateException e) {
        } catch (Exception e) {
            logError("Ошибка при записи в буфер обмена!");
        }
    }

    String getForeSelectedText(){
        WinDef.HWND hwnd = User32Ex.INSTANCE.GetForegroundWindow();
        String text = getSelectedText(hwnd);
        return text;
    }
    String getSelectedText(WinDef.HWND hwndTop){
        try {
            User32Ex.INSTANCE.SetForegroundWindow(hwndTop);
            Thread.sleep(200);
            /*char[] windowText = new char[512];
            User32Ex.INSTANCE.GetWindowText(hwndTop, windowText, 512);
            String windowTitle = Native.toString(windowText);*/

            String before = getClipboardText();
            controlC(); // emulate Ctrl C
            Thread.sleep(200);
            String text = getClipboardText();
            if(text.equals(before))
                text = "";//ничего не выделено в целевом окне
            setClipboardText(before);
            return text;
        } catch (Exception e){
            logError("Ошибка при чтении из буфера обмена!");
            return "";
        }
    }

    String getForeWindowTitle() {
        WinDef.HWND hwnd = User32Ex.INSTANCE.GetForegroundWindow();
        String windowTitle = GetWindowTitle(hwnd);
        return windowTitle;
    }
    String GetWindowTitle(WinDef.HWND hwnd) {
        try {
            char[] windowText = new char[512];
            User32Ex.INSTANCE.GetWindowText(hwnd, windowText, 512);
            String windowTitle = Native.toString(windowText);

            String before = getClipboardText();
            controlC(); // emulate Ctrl C
            Thread.sleep(100); // give it some time
            // restore what was previously in the clipboard
            setClipboardText(before);

            return windowTitle;
        } catch (Exception e){
            logError("Ошибка при получении текста окна!");
            return "";
        }
    }
}