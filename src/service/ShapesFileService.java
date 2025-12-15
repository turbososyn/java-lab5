package service;

import model.Shape;

import java.io.*;

public class ShapesFileService {
    public void save(Shape[] shapes, String path) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(shapes);
        }
    }

    public Shape[] load(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Object obj = ois.readObject();
            if (!(obj instanceof Shape[])) throw new IOException("File does not contain Shape[]");
            return (Shape[]) obj;
        }
    }
}
