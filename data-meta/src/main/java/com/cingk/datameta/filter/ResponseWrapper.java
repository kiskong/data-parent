package com.cingk.datameta.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream byteArrayOutputStream;
    private ServletOutputStream servletOutputStream;

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response The response to be wrapped
     * @throws IllegalArgumentException if the response is null
     */
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        byteArrayOutputStream = new ByteArrayOutputStream();
        servletOutputStream = new OutputStreamWrapper(byteArrayOutputStream);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutputStream;
    }

    @Override
    public void flushBuffer() throws IOException {
        if(servletOutputStream != null ){
            servletOutputStream.flush();
        }
    }

    public byte[] getContent() throws IOException {
        flushBuffer();
        return byteArrayOutputStream.toByteArray();
    }

    class OutputStreamWrapper extends ServletOutputStream{

        private ByteArrayOutputStream byteArrayOutputStream;
        public OutputStreamWrapper(ByteArrayOutputStream byteArrayOutputStream){
            this.byteArrayOutputStream = byteArrayOutputStream;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }

        @Override
        public void write(int b){
            byteArrayOutputStream.write(b);
        }
    }
}
