package com.dotseven.captchaservice.captchatest;

import javax.persistence.*;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

@Entity
@Table(name = "captcha_test")
public class CaptchaTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private static int TARGET_SIZE = 500;
    private static String SRC_IMGS_DIRECTORY = "./src_dataset/";

    @Column(name = "base64_image", nullable = false)
    @Type(type = "text")
    private String base64Image;

    @Column(name = "correct_answer1")
    private String correctAnswers1; // risposta 1 e risposta 2
    @Column(name = "correct_answer2")
    private String correctAnswers2;

    public CaptchaTest() {
        List<BufferedImage> img = null;

        img = getRandomBaseImage();
        BufferedImage composeImg = composeImage(img.get(0), img.get(1));
        base64Image = base64EncodeImage(composeImg);
    }

    public List<String> getCorrectAnswers() {
        List<String> correctAnswers = new ArrayList<String>();
        correctAnswers.add(correctAnswers1);
        correctAnswers.add(correctAnswers2);
        return correctAnswers;
    }

    public Long getId() {
        return id;
    }

    public String getBase64Image() {
        return base64Image;
    }

    private BufferedImage composeImage(BufferedImage fgImage, BufferedImage bgImage) {
        BufferedImage bi = new BufferedImage(TARGET_SIZE, TARGET_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gi = bi.createGraphics();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

        gi.drawImage(bgImage, 0, 0, TARGET_SIZE, TARGET_SIZE, null);
        gi.setComposite(ac);
        gi.drawImage(fgImage, 0, 0, TARGET_SIZE, TARGET_SIZE, null);

        gi.dispose();

        return bi;
    }

    private String base64EncodeImage(BufferedImage image) {

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            byte[] imageBytes = os.toByteArray();
            String imgToString = Base64.getEncoder().encodeToString(imageBytes);
            return imgToString;
        } catch (final IOException ioe) {
            System.err.println("Couldn't interpretate image from File");
            ioe.printStackTrace();
        }
        return null;
    }

    private List<BufferedImage> getRandomBaseImage() {
        File dataset = new File(SRC_IMGS_DIRECTORY);
        File[] folders = dataset.listFiles(File::isDirectory);
        int numFolders = folders.length;
        Random randomGenerator = new Random();
        int bgSubjectIndex = randomGenerator.nextInt(numFolders);
        int fgSubjectIndex = randomGenerator.nextInt(numFolders);

        while (fgSubjectIndex == bgSubjectIndex) {
            fgSubjectIndex = randomGenerator.nextInt(numFolders);
        }

        File bgSubjectFolder = folders[bgSubjectIndex];
        File fgSubjectFolder = folders[fgSubjectIndex];

        File[] bgSubjectFiles = bgSubjectFolder.listFiles(File::isFile);
        File[] fgSubjectFiles = fgSubjectFolder.listFiles(File::isFile);

        int bgId = randomGenerator.nextInt(bgSubjectFiles.length);
        int fgId = randomGenerator.nextInt(fgSubjectFiles.length);

        List<BufferedImage> images = new ArrayList<>();

        try {
            BufferedImage bgImage = ImageIO.read(bgSubjectFiles[bgId]);
            BufferedImage fgImage = ImageIO.read(fgSubjectFiles[fgId]);
            images.add(bgImage);
            images.add(fgImage);
        } catch (IOException e) {
            System.err.println("Couldn't interpret image from file");
            e.printStackTrace();
            System.exit(1);
        }

        correctAnswers1 = bgSubjectFolder.getName();
        correctAnswers2 = fgSubjectFolder.getName();

        return images;
    }
}
