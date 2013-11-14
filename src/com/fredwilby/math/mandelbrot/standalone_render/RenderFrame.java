package com.fredwilby.math.mandelbrot.standalone_render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.FileNotFoundException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import com.fredwilby.math.mandelbrot.color.BWColorModel;
import com.fredwilby.math.mandelbrot.color.ColorModel;
import com.fredwilby.math.mandelbrot.color.DirectHSVColorModel;
import com.fredwilby.math.mandelbrot.color.InterpolatedColorModel;
import com.fredwilby.math.mandelbrot.ui_legacy.RDEvent;

public class RenderFrame extends JFrame
{
    private final int jTextFieldWidth = 10;
    private JLabel lWidth, lHeight, ltli,ltlj, lbri, lbrj, lIt, lInvalid;
    private JTextField width, height, topLeft[], botRight[], it;
    
    private final String[] defaultValues = new String[] { "10000", "5714", "-2.5", "1.0", "1.0", "-1.0", "10000" };
    
    private JList<ColorModel> colors;
    
    private JButton go;
    private DefaultListModel<ColorModel> colorList;
    
    private JProgressBar progress;
    
    public static final int w = 500, h = 800;
    
    private ActionListener gogogo = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {

            new Thread( new Runnable() { public void run() {      
     
            Point2D.Double tl, br; 
            Dimension pv;
            long its;
                 
            try
            {
                tl = new Point2D.Double(Double.parseDouble(topLeft[0].getText()),
                                        Double.parseDouble(topLeft[0].getText()));
                
                br = new Point2D.Double(Double.parseDouble(botRight[0].getText()),
                                        Double.parseDouble(botRight[0].getText()));
                
                pv = new Dimension(Integer.parseInt(width.getText()), 
                                    Integer.parseInt(height.getText()));
                its = Long.parseLong(it.getText());
                
                RDEvent r = new RDEvent(pv, tl, br, its);
                
                ColorModel m = colors.getSelectedValue();
                
                if(m != null)
                {

                    LBLRenderer rend = new LBLRenderer(r, m, "render-"+System.nanoTime()+".png");
                    progress.setModel(rend);
                    lInvalid.setVisible(false);

                    rend.writeLines();
                }
                
            } catch(NumberFormatException nfe)
            {
                lInvalid.setVisible(true);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            
            
            } } ).start();
            
        }
        
    };
    
    
    public RenderFrame()
    {
        super("Mandelbrot Renderer");
        
        colorList = new DefaultListModel<ColorModel>();
        colorList.addElement(new BWColorModel());
        colorList.addElement(new DirectHSVColorModel());
        colorList.addElement(InterpolatedColorModel.wikiMap);
        
        setupUI();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private void setupUI()
    {                                        /* LC, CC, RC */
        JPanel layout = new JPanel(new MigLayout("", "[right 200px][300px]", ""));
        layout.setPreferredSize(new Dimension(w,h));
        
        lInvalid = new JLabel("Invalid Input");
        lInvalid.setForeground(Color.red);
        lInvalid.setVisible(false);
        layout.add(lInvalid, new CC().grow().wrap().spanX(2));
        
        lWidth = new JLabel("Width:");
        layout.add(lWidth);
        width = new JTextField(jTextFieldWidth);
        width.setText(defaultValues[0]);
        layout.add(width, getCC());
        
        lHeight = new JLabel("Height:");
        layout.add(lHeight);
        height = new JTextField(jTextFieldWidth);
        height.setText(defaultValues[1]);
        layout.add(height, getCC());
        
        topLeft = new JTextField[] { new JTextField(jTextFieldWidth), new JTextField(jTextFieldWidth) };
        topLeft[0].setText(defaultValues[2]);
        topLeft[1].setText(defaultValues[3]);
        ltli = new JLabel("Top left real:");
        layout.add(ltli);
        layout.add(topLeft[0], getCC());
        
        ltlj = new JLabel("imaginary:");
        layout.add(ltlj);
        layout.add(topLeft[1], getCC());        
        
        botRight = new JTextField[] { new JTextField(jTextFieldWidth), new JTextField(jTextFieldWidth) };
        botRight[0].setText(defaultValues[4]);
        botRight[1].setText(defaultValues[5]);
        lbri = new JLabel("Bottom Right real:");
        layout.add(lbri);
        layout.add(botRight[0], getCC());
        
        lbrj = new JLabel("imaginary:");
        layout.add(lbrj);
        layout.add(botRight[1], getCC());
        
        lIt = new JLabel("Maximum Number of iterations: ");
        layout.add(lIt);
        it = new JTextField(jTextFieldWidth);
        it.setText(defaultValues[6]);
        layout.add(it, getCC());
        
        colors = new JList<ColorModel>(colorList);
        colors.setSelectedIndex(2);
        layout.add(colors, new CC().grow().spanX(2).wrap());
        
        go = new JButton("Start Render");
        go.addActionListener(gogogo);
        layout.add(go, new CC().grow().spanX(2).wrap());
        
        progress = new JProgressBar();
        layout.add(progress, new CC().grow().spanX(2).wrap());
        
        getContentPane().add(layout);
        pack();
        setVisible(true);
        
    }
    
    private CC getCC()
    {
        return new CC().grow().wrap();
    }
    

    public static void main(String[] args)
    {
        new RenderFrame();
    }

}
