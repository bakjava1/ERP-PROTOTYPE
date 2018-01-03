package at.ac.tuwien.sepm.assignment.group02.server.service;

import at.ac.tuwien.sepm.assignment.group02.server.entity.Lumber;
import at.ac.tuwien.sepm.assignment.group02.server.entity.OptimisationResult;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Timber;
import at.ac.tuwien.sepm.assignment.group02.server.entity.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class OptimierungTest extends JFrame{

    private static final long serialVersionUID = 1L;


    public static void main(String[] args) {

        List<Timber> rundholzboxen = new ArrayList<>();
        List<Lumber> nebenauftraege = new ArrayList<>();

        Timber t1 = new Timber();
        Timber t2 = new Timber();
        Timber t3 = new Timber();
        Timber t4 = new Timber();
        Timber t5 = new Timber();
        Timber t6 = new Timber();
        Timber t7 = new Timber();

        t1.setDiameter(300);
        t2.setDiameter(320);
        t3.setDiameter(340);
        t4.setDiameter(350);
        t5.setDiameter(360);
        t6.setDiameter(380);
        t7.setDiameter(400);

        rundholzboxen.add(t1);
        rundholzboxen.add(t2);
        rundholzboxen.add(t3);
        rundholzboxen.add(t4);
        rundholzboxen.add(t5);
        rundholzboxen.add(t6);
        rundholzboxen.add(t7);

        /*
        Lumber l1 = new Lumber();
        Lumber l2 = new Lumber();
        l1.setSize(23);
        l1.setWidth(189);
        l2.setSize(39);
        l2.setWidth(150);
        nebenauftraege.add(l1);
        nebenauftraege.add(l2);
        */

        Lumber hauptauftrag = new Lumber();
        hauptauftrag.setSize(46);
        hauptauftrag.setWidth(245);

        OptimisationAlgorithmServiceImpl optimation = new OptimisationAlgorithmServiceImpl(rundholzboxen,nebenauftraege,hauptauftrag);

        try {
            optimation.calculateCut();


            OptimisationResult r = optimation.getResult();

            System.out.println("Durchmesser: "+ r.getBox().getDiameter());

            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {

                    if (r.getRectangles() != null && !r.getRectangles().isEmpty()) {
                        new OptimierungTest().DrawShapes(r);
                    }
                }


            });




        }catch (Exception e){
            System.err.println(e.getMessage());
        }


    }


    public void DrawShapes(OptimisationResult r) {
        int diameter = r.getBox().getDiameter();
        List <Rectangle> rectangles = r.getRectangles();


        setSize(new Dimension(diameter+50, diameter+50));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);



        JPanel p = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                Color border = new Color(50, 50, 50);
                g2.setColor(border);

                Shape circle = new Ellipse2D.Double(0, 0, diameter, diameter);
                g2.draw(circle);



                for (Rectangle rectangle : rectangles) {


                    Shape rect = new java.awt.Rectangle((int)rectangle.getxCoordinate(), (int)rectangle.getyCoordinate(), (int)rectangle.getWidth(), (int)rectangle.getHeight());

                    switch (rectangle.getColor()){
                        case"green" :
                            g2.setColor(new Color(45, 138, 65));
                            g2.fill(rect);
                            g2.setColor(border);
                            g2.draw(rect);
                            break;
                        case"red" :
                            g2.setColor(new Color(222, 113, 103));
                            g2.fill(rect);
                            g2.setColor(border);
                            g2.draw(rect);
                            break;
                        case"blue" :
                            g2.setColor(new Color(40, 145, 217));
                            g2.fill(rect);
                            g2.setColor(border);
                            g2.draw(rect);
                            break;
                        default:
                            break;

                    }



                }


            }
        };


        setTitle("My Shapes");
        this.getContentPane().add(p);


    }




}
