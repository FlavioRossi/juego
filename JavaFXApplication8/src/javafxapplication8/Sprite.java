package javafxapplication8;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class Sprite
{   
    private Image image;
    private double positionX;
    private double positionY;    
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private double angulo;

    public Sprite()
    {
        positionX = 0;
        positionY = 0;
        angulo = 0;
    }

    public void setImage(Image i)
    {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }
    
    public void setImage(String filename)
    {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double x, double y)
    {
        positionX = x;
        positionY = y;
    }
    
    public void setRotate(GraphicsContext gc){
        double mitadx = this.width / 2;
        double mitady = this.height / 2;
        
        gc.save();
        gc.translate(positionX +  mitadx, positionY + mitady); //centrar la imagen
        gc.rotate(angulo); //angulo de rotacion  
        gc.drawImage(image, -mitadx, -mitady);    
        gc.restore();
    }

    public void setAngulo(double angulo){
        this.angulo += angulo;
        if(this.angulo>360){
            this.angulo = this.angulo - 360;
        }else if (this.angulo < 0) {
            this.angulo = 360 - angulo;
        }
    }
    
    public void update(double time, double velocidad)
    {
        double angulo0 = Math.toRadians(angulo) - Math.atan2(positionY, positionX);
        positionX += Math.cos(angulo0) * (velocidad * time);
        positionY += Math.sin(angulo0) * (velocidad * time);
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage(image, positionX, positionY);
        gc.applyEffect(new DropShadow(2, 3, 3, Color.GRAY));
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }
    
    public String toString()
    {
        return " Position: [" + positionX + "," + positionY + "]" 
        + " Velocity: [" + velocityX + "," + velocityY + "]"
        + " Angulo: [" + angulo + "]";
    }
    
}