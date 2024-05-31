import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageViewerGUI extends JFrame implements ActionListener {
    JButton selectFileButton;
    JButton showImageButton;
    JButton resizeButton;
    JButton grayscaleButton;
    JButton brightnessButton;
    JButton closeButton;
    JButton showResizeButton;
    JButton showBrightnessButton;
    JButton backButton;
    JTextField widthTextField;
    JTextField heightTextField;
    JTextField brightnessTextField;
    String filePath = ".//";
    File file;
    JFileChooser fileChooser = new JFileChooser(filePath);
    int h = 900;
    int w = 1200;
    float brightenFactor = 1;

    ImageViewerGUI() {
        selectFileButton = new JButton("Choose Image");
        selectFileButton.addActionListener(this);
        showImageButton = new JButton("Show Image");
        showImageButton.addActionListener(this);
        brightnessButton = new JButton("Brightness");
        brightnessButton.addActionListener(this);
        grayscaleButton = new JButton("Gray scale");
        grayscaleButton.addActionListener(this);
        resizeButton = new JButton("Resize");
        resizeButton.addActionListener(this);
        closeButton = new JButton("Exit");
        closeButton.addActionListener(this);
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        showBrightnessButton = new JButton("Result");
        showBrightnessButton.addActionListener(this);
        brightnessTextField = new JTextField();
        showResizeButton = new JButton("Show result");
        showResizeButton.addActionListener(this);

        FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.setFileFilter(imageFilter);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setVisible(true);
        this.setResizable(true);

        mainPanel();
    }

    public void mainPanel() {
        // Create main panel for adding to Frame
        JPanel mainPanel = new JPanel();
        //mainPanel.setLayout(null);

        // Create Grid panel for adding buttons to it, then add it all to main panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 2));


        // Adding all buttons to Grid panel
        buttonsPanel.add(this.selectFileButton);
        buttonsPanel.add(this.showImageButton);
        buttonsPanel.add(this.brightnessButton);
        buttonsPanel.add(this.grayscaleButton);
        buttonsPanel.add(this.resizeButton);
        buttonsPanel.add(this.closeButton);

        // add Grid panel that contains 6 buttons to main panel
        mainPanel.add(buttonsPanel);

        // add main panel to our frame
        this.add(mainPanel);
    }

    public void resizePanel() {
        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(null);

        JLabel resizeSection = new JLabel("ResizeSection");
        heightTextField = new JTextField();
        widthTextField = new JTextField();
        JLabel Width = new JLabel("Width:");
        JLabel Height = new JLabel("Height:");
        resizeSection.setBounds(270, 0, 500, 100);
        backButton.setBounds(60, 190, 100, 30);
        showResizeButton.setBounds(450, 190, 200, 30);
        Width.setBounds(150, 35, 500, 100);
        Height.setBounds(150, 80, 500, 100);
        heightTextField.setBounds(260, 70, 100, 30);
        widthTextField.setBounds(260, 115, 100, 30);

        resizePanel.add(resizeSection);
        resizePanel.add(heightTextField);
        resizePanel.add(widthTextField);
        resizePanel.add(Height);
        resizePanel.add(Width);
        resizePanel.add(heightTextField);
        resizePanel.add(widthTextField);
        resizePanel.add(backButton);
        resizePanel.add(showResizeButton);


        this.add(resizePanel);
    }

    public void brightnessPanel() {
        JPanel brightnessPanel = new JPanel();
        brightnessPanel.setLayout(null);
        JLabel enterf = new JLabel("Enter f (must be between 0 and 1) ");

        enterf.setBounds(50, 50, 500, 100);
        backButton.setBounds(60, 150, 100, 30);
        showBrightnessButton.setBounds(300, 150, 100, 30);
        brightnessTextField.setBounds(300, 85, 100, 30);
        brightnessPanel.add(brightnessTextField);
        brightnessPanel.add(enterf);
        brightnessPanel.add(backButton);
        brightnessPanel.add(showBrightnessButton);
        this.add(brightnessPanel);
    }

    public void chooseFileImage() {

        fileChooser.setAcceptAllFileFilterUsed(false);
        int option = fileChooser.showOpenDialog(ImageViewerGUI.this);
        if (option == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
    }

    public void showOriginalImage() {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        try {
            if (this.file != null) {
                BufferedImage bufferedImage = ImageIO.read(this.file);
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                JLabel tempLabel = new JLabel();
                tempLabel.setIcon(imageIcon);
                tempPanel.add(tempLabel);
            } else {
                JLabel error = new JLabel("Nothing to show");
                tempPanel.add(error);
            }
        } catch (IOException e) {
            System.out.println("Couldn't show");
        }

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void grayScaleImage() {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        try {
            if (this.file != null) {
                ImageFilter filter = new GrayFilter(false, 50);
                BufferedImage bufferedImage = ImageIO.read(this.file);
                ImageProducer producer = new FilteredImageSource(bufferedImage.getSource(), filter);
                Image mage = Toolkit.getDefaultToolkit().createImage(producer);
                ImageIcon imageIcon = new ImageIcon(mage);
                JLabel tempLabel = new JLabel();
                tempLabel.setIcon(imageIcon);
                tempPanel.add(tempLabel);
            } else {
                JLabel error = new JLabel("Nothing to show");
                tempPanel.add(error);
            }
        } catch (IOException ex) {
            System.out.println("Could't show image");
        }

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void showResizeImage(int w, int h) {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JLabel tempLabel = new JLabel();
        try {
            if (this.file != null) {
                BufferedImage bufferedImage = ImageIO.read(this.file);
                ImageIcon imageIcon = new ImageIcon(bufferedImage.getScaledInstance(w, h, Image.SCALE_DEFAULT));
                tempLabel.setIcon(imageIcon);
                tempPanel.add(tempLabel);
            } else {
                JLabel error = new JLabel("Nothing to show");
                tempPanel.add(error);
            }
        } catch (IOException e) {
            System.out.println("Could't show image");
        }


        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    public void showBrightnessImage(float f) {
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        JLabel tempLabel = new JLabel();

        try {
            if (this.file != null) {
                BufferedImage bufferedImage = ImageIO.read(this.file);
                RescaleOp op = new RescaleOp(f, 0, null);
                bufferedImage = op.filter(bufferedImage, bufferedImage);
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                tempLabel.setIcon(imageIcon);
                tempPanel.add(tempLabel);
            } else {
                JLabel error = new JLabel("Nothing to show");
                tempPanel.add(error);
            }
        } catch (IOException e) {
            System.out.println("Couldn't show");
        }


        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resizeButton) {
            this.getContentPane().removeAll();
            this.resizePanel();
            this.revalidate();
            this.repaint();

        } else if (e.getSource() == showImageButton) {
            showOriginalImage();

        } else if (e.getSource() == grayscaleButton) {
            grayScaleImage();

        } else if (e.getSource() == showResizeButton) {
            try {
                w = Integer.parseInt(widthTextField.getText());
                h = Integer.parseInt(heightTextField.getText());
                showResizeImage(w, h);
            } catch (Exception ex) {
                System.out.println("Wrong value");
            }

        } else if (e.getSource() == brightnessButton) {
            this.getContentPane().removeAll();
            brightnessPanel();
            this.revalidate();
            this.repaint();
        } else if (e.getSource() == showBrightnessButton) {
            try {
                brightenFactor = Float.parseFloat(brightnessTextField.getText());
                if ((0 <= brightenFactor) && (brightenFactor <= 1)) {
                    showBrightnessImage(brightenFactor);
                } else {
                    System.out.println("out of range");
                }
            } catch (Exception ex) {
                System.out.println("Wrong value");
            }
        } else if (e.getSource() == selectFileButton) {
            chooseFileImage();

        } else if (e.getSource() == closeButton) {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        } else if (e.getSource() == backButton) {
            this.getContentPane().removeAll();
            this.mainPanel();
            this.revalidate();
            this.repaint();
        }
    }
}