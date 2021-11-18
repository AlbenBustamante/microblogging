import com.danicode.gui.model.GUIDialog;
import com.danicode.gui.model.GUIWindow;

import javax.swing.*;
import java.awt.*;

public class MainTest {
    public static void main(String... test) {
        SwingUtilities.invokeLater(TestWindow::new);
    }
}

class TestWindow extends GUIWindow {
    public TestWindow() {
        super(640, 480, "Prueba", true, new FlowLayout(FlowLayout.CENTER));
    }

    @Override
    protected void close() {
        System.out.println("Hasta pronto!");
        System.exit(0);
    }

    @Override
    protected void init() {
        JButton boton = new JButton("Púlsame");
        boton.addActionListener(l -> new TestDialog(this));
        this.getContentPane().add(boton);
    }
}

class TestDialog extends GUIDialog {

    public TestDialog(JFrame owner) {
        super(owner, 500, 700, "Test", false, true, new FlowLayout(FlowLayout.CENTER));
    }

    @Override
    protected void close() {
        System.out.println("oleee");
        this.dispose();
    }

    @Override
    protected void init() {
        this.getContentPane().add(new JLabel("HOLAAAA"));
    }
}