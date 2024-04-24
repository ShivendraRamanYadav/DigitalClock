import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class EnhancedDigitalClock extends JFrame {
    private JLabel timeLabel;
    private JLabel dateLabel;
    private JComboBox<String> formatComboBox;
    private JButton colorButton;
    private JButton modeButton;
    private SimpleDateFormat[] timeFormats = {
        new SimpleDateFormat("HH:mm:ss"),
        new SimpleDateFormat("hh:mm:ss a"),
        new SimpleDateFormat("HH:mm")
    };
    private Color backgroundColor = Color.WHITE;
    private boolean isDarkMode = false;

    public EnhancedDigitalClock() {
        setTitle("Enhanced Digital Clock");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1));

        timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        dateLabel = new JLabel();
        dateLabel.setHorizontalAlignment(JLabel.CENTER);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        formatComboBox = new JComboBox<>(new String[]{"24-Hour", "12-Hour", "HH:mm"});
        formatComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });

        colorButton = new JButton("Change Color");
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeBackgroundColor();
            }
        });

        modeButton = new JButton("Toggle Mode");
        modeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleMode();
            }
        });

        panel.add(timeLabel);
        panel.add(dateLabel);
        panel.add(formatComboBox);
        panel.add(colorButton);
        panel.add(modeButton);

        add(panel);

        updateTime();

        setVisible(true);
    }

    private void updateTime() {
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    int selectedFormatIndex = formatComboBox.getSelectedIndex();
                    SimpleDateFormat sdf = timeFormats[selectedFormatIndex];
                    SimpleDateFormat dateSdf = new SimpleDateFormat("dd-MM-yyyy");
                    timeLabel.setText(sdf.format(new Date()));
                    dateLabel.setText(dateSdf.format(new Date()));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    private void changeBackgroundColor() {
        Color newColor = JColorChooser.showDialog(this, "Choose Background Color", backgroundColor);
        if (newColor != null) {
            backgroundColor = newColor;
            getContentPane().setBackground(backgroundColor);
        }
    }

    private void toggleMode() {
        isDarkMode = !isDarkMode;
        if (isDarkMode) {
            timeLabel.setForeground(Color.WHITE);
            dateLabel.setForeground(Color.WHITE);
            getContentPane().setBackground(Color.BLACK);
        } else {
            timeLabel.setForeground(Color.BLACK);
            dateLabel.setForeground(Color.BLACK);
            getContentPane().setBackground(Color.WHITE);
        }
    }

    public static void main(String[] args) {
        new EnhancedDigitalClock();
    }
}

