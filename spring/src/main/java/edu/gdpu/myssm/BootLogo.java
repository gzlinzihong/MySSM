package edu.gdpu.myssm;

import edu.gdpu.myssm.utils.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 23:24:01
 */
public class BootLogo {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void display(){
        InputStream inputStream = ResourceUtils.loadInClassPath("logo.txt");
        byte[] bytes = new byte[1024];
        try {
            int read =0;
            inputStream.read(bytes);
            logger.info(new String(bytes,0,bytes.length));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
