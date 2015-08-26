package carpreview;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * Car preview.
 *
 * @author Jan Stola
 */
public class CarPreview extends JComponent {
    private static final int IMAGE_WIDTH = 90;
    private static final int IMAGE_HEIGHT = 90;
    
    private Color color = Color.RED;
    private Integer modernness = 0;
    private String make = null;
    private Integer tireSize = 38;
    private Integer tireType = 0;
    private Boolean spoiler = false;
    private Boolean sunRoof = false;
    private int lift;
    
    private Image image;

    private float wheelAngle;
    
    public CarPreview() {
        setFont(UIManager.getFont("Label.font").deriveFont(25.0f));
        setPreferredSize(new Dimension(320, 145));
    }
    
    public void setFace(Image image) {
        if (image == null) {
            this.image = null;
        } else {
            this.image = ImageScaler.scaleImage(image, IMAGE_WIDTH, IMAGE_HEIGHT);
        }
    }
    
    public void setColor(String color) {
        if ("gray".equals(color)) {
            this.color = Color.GRAY;
        } else if ("silver".equals(color)) {
            this.color = Color.LIGHT_GRAY;
        } else if ("white".equals(color)) {
            this.color = Color.WHITE;
        } else if ("red".equals(color)) {
            this.color = Color.RED;
        } else if ("orange".equals(color)) {
            this.color = Color.ORANGE;
        } else if ("blue".equals(color)) {
            this.color = Color.BLUE;
        } else if ("black".equals(color)) {
            this.color = Color.BLACK;
        } else if ("yellow".equals(color)) {
            this.color = Color.YELLOW;
        } else if ("brown".equals(color)) {
            this.color = new Color(128,64,0);
        } else if ("green".equals(color)) {
            this.color = Color.GREEN;
        } else {
            // Fallback
            this.color = Color.RED;
        }
        repaint();
    }
    
    public String getColor() {
        if (Color.GRAY.equals(color)) {
            return "gray";
        } else if (Color.LIGHT_GRAY.equals(color)) {
            return "silver";
        } else if (Color.WHITE.equals(color)) {
            return "white";
        } else if (Color.RED.equals(color)) {
            return "red";
        } else if (Color.ORANGE.equals(color)) {
            return "orange";
        } else if (Color.BLUE.equals(color)) {
            return "blue";
        } else if (Color.BLACK.equals(color)) {
            return "black";
        } else if (Color.YELLOW.equals(color)) {
            return "yellow";
        } else if (new Color(128,64,0).equals(color)) {
            return "brown";
        } else if (Color.GREEN.equals(color)) {
            return "green";
        } else {
            // Fallback
            return "unsupported";
        }
    }
    
    public void setModernness(Integer modernness) {
        if (modernness == null) modernness = 0;
        this.modernness = modernness;
        repaint();
    }

    public Integer getModernness() {
        return modernness;
    }
    
    public void setMake(String make) {
        this.make = make;
        repaint();
    }

    public String getMake() {
        return make;
    }
    
    public void setTireSize(Integer tireSize) {
        if (tireSize == null) tireSize = 38;
        this.tireSize = tireSize;
        repaint();
    }
    
    public Integer getTireSize() {
        return tireSize;
    }
    
    public Integer getTireType() {
        return tireType;
    }
    
    public void setTireType(Integer tireType) {
        if (tireType == null) tireType = 0;
        this.tireType = tireType;
        repaint();
    }

    public Boolean getSpoiler() {
        return spoiler;
    }
    
    public void setSpoiler(Boolean spoiler) {
        if (spoiler == null) spoiler = false;
        this.spoiler = spoiler;
        repaint();
    }
    
    public Boolean getSunRoof() {
        return sunRoof;
    }
    
    public void setSunRoof(Boolean sunRoof) {
        if (sunRoof == null) sunRoof = false;
        this.sunRoof = sunRoof;
        repaint();
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.transform(AffineTransform.getTranslateInstance(-50+(getWidth()-300)/2, 10+(getHeight()-130)/2));
        g2.transform(AffineTransform.getScaleInstance(0.5, 0.5));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // the body of the car
        int tyreSize = (int)(tireSize/100.0*8+12)*3;
        Shape botRound = new RoundRectangle2D.Double(100, 100, 600, 200, modernness, modernness);
        Shape botSquare = new Rectangle2D.Double(100, 100, 600, 100-0.5*modernness);
        Area bottom = new Area(botRound);
        bottom.intersect(new Area(botSquare));
        GeneralPath top = new GeneralPath();
        top.moveTo(200, 100);
        top.lineTo(250, modernness*0.4f);
        top.lineTo(550-modernness*0.8f, modernness*0.4f);
        top.lineTo(600, 100);
        top.closePath();
        Shape leftWheel = new Ellipse2D.Double(250-tyreSize, 200-0.5*modernness-tyreSize, 2*tyreSize, 2*tyreSize);
        Shape rightWheel = new Ellipse2D.Double(550-tyreSize, 200-0.5*modernness-tyreSize, 2*tyreSize, 2*tyreSize);
        GeneralPath leftWindow = new GeneralPath();
        leftWindow.moveTo(220, 100);
        leftWindow.lineTo(270-50*20/(100-modernness*0.4f), modernness*0.4f+20);
        leftWindow.lineTo(390-modernness, modernness*0.4f+20);
        leftWindow.lineTo(390-modernness, 100);
        leftWindow.closePath();
        GeneralPath rightWindow = new GeneralPath();
        rightWindow.moveTo(410-modernness, modernness*0.4f+20);
        rightWindow.lineTo(530+50*20/(100-modernness*0.4f)-modernness*0.8f, modernness*0.4f+20);
        rightWindow.lineTo(580-modernness*0.1f, 100);
        rightWindow.lineTo(410-modernness, 100);
        rightWindow.closePath();
        Area area = new Area(bottom);
        Area topa = new Area(top);
        area.exclusiveOr(topa);
        area.subtract(new Area(leftWheel));
        area.subtract(new Area(rightWheel));
        area.subtract(new Area(leftWindow));
        area.subtract(new Area(rightWindow));
        g2.translate(0, -lift);
        
        
        int rightWindowMinX = (410-modernness);
        int rightWindowMaxX = (int)(530+50*20/(100-modernness*0.4f)-modernness*0.8f);
        int rightWindowCenterX = (int)((rightWindowMaxX - rightWindowMinX) / 2) + rightWindowMinX;
        
        int rightWindowMinY = (int)(modernness*0.4f+20);
        int rightWindowMaxY = 100;
        int rightWindowCenterY = (int)((rightWindowMaxY - rightWindowMinY) / 2) + rightWindowMinY;

        g.drawImage(image, 
                rightWindowMaxX - IMAGE_WIDTH, 
                rightWindowMaxY - IMAGE_HEIGHT + 10, null);

        g2.setColor(color);
        g2.fill(area);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(5, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
        g2.draw(area);

        // spoiler
        g2.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        if (spoiler) {
            g2.drawLine(150, 100, 130, 60);
            g2.drawLine(110, 60, 150, 60);
        }
        
        // roof window
        if (sunRoof) {
            g2.drawLine(480-(int)modernness, (int)(modernness*0.4), 440-(int)modernness, (int)(modernness*0.4-20));
        }
        
        // make
        if (make != null) {
            if (Color.BLACK.equals(color) || new Color(128,64,0).equals(color)) {
                g2.setColor(Color.WHITE);
            }
            int width = g2.getFontMetrics().stringWidth(make);
            g2.drawString(make, 400-width/2, (int)(150-0.2*modernness));
            g2.setColor(Color.BLACK);
        }

        g2.translate(0, lift);

        // wheels
        double leftWheelX = 250-tyreSize+10;
        double leftWheelY = 200-0.5*modernness-tyreSize+10;
        double rightWheelX = 550-tyreSize+10;
        double rightWheelY = 200-0.5*modernness-tyreSize+10;
        double wheelSize = 2*tyreSize-20;
        int halfWheelSize = (int)(wheelSize / 2);
        leftWheel = new Ellipse2D.Double(leftWheelX, leftWheelY, wheelSize, wheelSize);
        rightWheel = new Ellipse2D.Double(rightWheelX, rightWheelY, wheelSize, wheelSize);
        if (tireType == 0) {
            drawSpokedWheel(g2, (int)leftWheelX, (int)leftWheelY, (int)wheelSize, tyreSize);
            drawSpokedWheel(g2, (int)rightWheelX, (int)rightWheelY, (int)wheelSize, tyreSize);
        } else if (tireType == 1) {
            g2.fill(leftWheel);
            g2.fill(rightWheel);            
        } else if (tireType == 2) {
            g2.setStroke(new BasicStroke(15, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
            g2.draw(leftWheel);
            g2.draw(rightWheel);            
        }

    }
    
    private void drawSpokedWheel(Graphics2D g2, int x, int y, int wheelSize, int tyreSize) {
        int halfWheelSize = wheelSize / 2;
        g2.translate(x + halfWheelSize, y + halfWheelSize);
        g2.draw(new Ellipse2D.Double(-halfWheelSize, -halfWheelSize, wheelSize, wheelSize));
        g2.rotate(wheelAngle);

        g2.drawLine(-halfWheelSize, -halfWheelSize + tyreSize - 10,
                -20+2*tyreSize - halfWheelSize,  tyreSize - 10 - halfWheelSize);
        g2.drawLine(-halfWheelSize + tyreSize - 10, -halfWheelSize ,
                -halfWheelSize + tyreSize -10, -halfWheelSize + 2 * tyreSize-20);
        int shift = (int)(0.707*(tyreSize-10));
        g2.drawLine(-halfWheelSize - shift + tyreSize - 10, -halfWheelSize -shift + tyreSize - 10,
                -halfWheelSize + shift + tyreSize - 10, -halfWheelSize + shift + tyreSize - 10);
        g2.drawLine(-halfWheelSize + shift + tyreSize - 10, -halfWheelSize - shift + tyreSize - 10,
                -halfWheelSize - shift + tyreSize - 10, -halfWheelSize + shift + tyreSize - 10);

        g2.rotate(-wheelAngle);
        g2.translate(-(x + halfWheelSize), -(y + halfWheelSize));
    }
    
    public int getTireDisplaySize() {
        return (int)(tireSize/100.0*8+12)*3;
    }
    
    public void setWheelAngle(float angle) {
        this.wheelAngle = angle;
    }

    public void setLift(int lift) {
        this.lift = lift;
        repaint();
    }
    
    public int getBottomOfTire() {
        int tyreSize = getTireDisplaySize();
        return (int)(((200-0.5*modernness-tyreSize+10) + (2*tyreSize-20)) / 2) + (10+(getHeight()-130)/2);
    }
    
}
