package service;

import java.io.*;

public class CipherService {

    public void encrypt(String inPath, String outPath, char key) throws IOException {
        try (Reader r = new FilteredReader(new FileReader(inPath), key, true);
             Writer w = new FileWriter(outPath)) {
            pipe(r, w);
        }
    }

    public void decrypt(String inPath, String outPath, char key) throws IOException {
        try (Reader r = new FilteredReader(new FileReader(inPath), key, false);
             Writer w = new FileWriter(outPath)) {
            pipe(r, w);
        }
    }

    private void pipe(Reader r, Writer w) throws IOException {
        char[] buf = new char[4096];
        int n;
        while ((n = r.read(buf)) != -1) {
            w.write(buf, 0, n);
        }
    }

    private static class FilteredReader extends FilterReader {
        private final int key;
        private final boolean encrypt;

        protected FilteredReader(Reader in, char key, boolean encrypt) {
            super(in);
            this.key = (int) key;
            this.encrypt = encrypt;
        }

        @Override
        public int read() throws IOException {
            int c = super.read();
            if (c == -1) return -1;
            int res = encrypt ? (c + key) : (c - key);
            return res & 0xFFFF;
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            int n = super.read(cbuf, off, len);
            if (n == -1) return -1;
            for (int i = off; i < off + n; i++) {
                int c = cbuf[i];
                int res = encrypt ? (c + key) : (c - key);
                cbuf[i] = (char) (res & 0xFFFF);
            }
            return n;
        }
    }
}
