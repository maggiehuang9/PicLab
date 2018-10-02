import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
    ///////////////////// constructors //////////////////////////////////

    /**
     * Constructor that takes no arguments 
     */
    public Picture ()
    {
        /* not needed but use it to show students the implicit call to super()
         * child constructors always call a parent constructor 
         */
        super();  
    }

    /**
     * Constructor that takes a file name and creates the picture 
     * @param fileName the name of the file to create the picture from
     */
    public Picture(String fileName)
    {
        // let the parent class handle this fileName
        super(fileName);
    }

    /**
     * Constructor that takes the width and height
     * @param height the height of the desired picture
     * @param width the width of the desired picture
     */
    public Picture(int height, int width)
    {
        // let the parent class handle this width and height
        super(width,height);
    }

    /**
     * Constructor that takes a picture and creates a 
     * copy of that picture
     * @param copyPicture the picture to copy
     */
    public Picture(Picture copyPicture)
    {
        // let the parent class do the copy
        super(copyPicture);
    }

    /**
     * Constructor that takes a buffered image
     * @param image the buffered image to use
     */
    public Picture(BufferedImage image)
    {
        super(image);
    }

    ////////////////////// methods ///////////////////////////////////////

    /**
     * Method to return a string with information about this picture.
     * @return a string with information about the picture such as fileName,
     * height and width.
     */
    public String toString()
    {
        String output = "Picture, filename " + getFileName() + 
            " height " + getHeight() 
            + " width " + getWidth();
        return output;

    }

    /**
     * Method to set red and green to 0.
     */
    public void keepOnlyBlue()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setGreen(0);
                pixelObj.setRed(0);
            }
        }
    }

    /**
     * Method to modify the pixel colors to make the fish easier to see.
     */
    public void fixUnderwater()
    {
         Pixel[][] pixels = this.getPixels2D(); 
      
         int averageR,averageG,averageB,numPixels; 
         int maxR,maxG,maxB,minR,minG,minB;
         
         averageR=averageG=averageB=numPixels=0;
         maxR =maxG=maxB=0;
         minR =minG=minB=255;
            
         // find the average/max/min RGB values in the fish area
         for (int row = 27; row < 38; row++) 
         { 
             for (int col = 177; col < 191; col++) 
             { 
                 numPixels++; 
                  
                 Pixel myPixel = pixels[row][col]; 
                  
                 averageR += myPixel.getRed(); 
                 averageG += myPixel.getGreen(); 
                 averageB += myPixel.getBlue(); 
                 
                 maxR = Math.max(maxR, myPixel.getRed()); 
                 maxG = Math.max(maxG, myPixel.getGreen());
                 maxB = Math.max(maxB, myPixel.getBlue());
                 minR = Math.min(minR, myPixel.getRed());
                 minG = Math.min(minG, myPixel.getGreen());
                 minB = Math.min(minB, myPixel.getBlue()); 
                  
             } 
         } 
          
         averageR = averageR / numPixels; 
         averageG = averageG / numPixels; 
         averageB = averageB / numPixels; 
          
         Color fishColor = new Color(averageR, averageG, averageB); 
          
         // calculates the distance 
         int redDistance = (maxR - minR); 
         int greenDistance = (maxG - minG); 
         int blueDistance = (maxB - minB); 
         
         // compute the coler ange on the fish area
         double range = Math.sqrt(redDistance * redDistance + 
                                  greenDistance * greenDistance + 
                                  blueDistance * blueDistance); 
 
         // modify the image, add mnore blue to fish area, add more red to non-fish area 
         for (int row = 0; row < pixels.length; row++) 
         { 
           for (int col = 0; col < pixels[0].length; col++)
           { 
               Pixel myPixel = pixels[row][col]; 
         
               // if color at this Pixel close to the fish color, make it more blue, otherwise, make it more red
               if (pixels[row][col].colorDistance(fishColor) < range*1.2) // if color close to the fish color, make it more blue
               { 
                   myPixel.setBlue(myPixel.getBlue() + 80); 
               } 
               else 
               { 
                   myPixel.setRed(myPixel.getRed() + 80); 
               } 
           } 
         } 
    }
    
    /**
     * Method to set red, blue, and green values to the average of current red, green and blue values.
     */
    public void grayscale()
    {
        Pixel[][] pixels = this.getPixels2D();

        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                int average = (pixelObj.getGreen() + pixelObj.getBlue() + pixelObj.getRed())/3;
                pixelObj.setGreen(average);
                pixelObj.setRed(average);
                pixelObj.setBlue(average);
            }
        }
    }

    /**
     * Method to set the red value to 255 minus the current red value, the green value to 255 minus the current green value and the blue value to 255 minus the current blue value. 

     */
    public void negate()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setGreen(255 - pixelObj.getGreen());
                pixelObj.setRed(255 - pixelObj.getRed());
                pixelObj.setBlue(255 - pixelObj.getBlue());
            }
        }
    }

    /** Method to set the blue to 0 */
    public void zeroBlue()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setBlue(0);
            }
        }
    }

    /** Method that mirrors the picture around a 
     * vertical mirror in the center of the picture
     * from left to right */
    public void mirrorVertical()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                rightPixel.setColor(leftPixel.getColor());
            }
        } 
    }

    /** Method that mirrors the picture around a 
     * vertical mirror in the center of the picture
     * from right to left */
    public void mirrorVerticalRightToLeft()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                leftPixel.setColor(rightPixel.getColor());
            }
        } 
    }

    /** Method that mirrors the picture around a 
     * horizontal mirror in the center of the picture
     * from top to bottom */
    public void mirrorHorizontal()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        int length = pixels.length;
        for (int row = length - 1; row > length / 2; row--)
        {
            for (int col = 0; col < pixels[row].length; col++)
            {
                bottomPixel = pixels[row][col];
                topPixel = pixels[length - 1 - row][col];
                bottomPixel.setColor(topPixel.getColor());
            }
        } 
    }

    /** Method that mirrors the picture around a 
     * horizontal mirror in the center of the picture
     * from bottom to top */
    public void mirrorHorizontalBotToTop()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        int length = pixels.length;
        for (int row = 0; row < length / 2; row++)
        {
            for (int col = 0; col < pixels[row].length; col++)
            {
                topPixel = pixels[row][col];
                bottomPixel = pixels[length - 1 - row][col];
                topPixel.setColor(bottomPixel.getColor());
            }
        } 
    }

    /** Method that mirrors the picture around a 
     * diagonal mirror from bottom left corner to top right corner*/
    public void mirrorDiagonal() 
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel topRightPixel = null;
        Pixel bottomLeftPixel = null;
        int max;
        if (pixels.length < pixels[0].length) 
        { 
            max = pixels.length; 
        }
        else 
        {
            max = pixels[0].length; 
        }

        for (int row = 0; row < max; row++)
        {
            for (int col = row; col < max; col++)
            {
                topRightPixel = pixels[row][col];
                bottomLeftPixel = pixels[col][row];
                topRightPixel.setColor(bottomLeftPixel.getColor());
            }
        }
    }
    
    /** Method that mirrors the seagull to the
     * right so that there are two seagulls near each other*/
    public void mirrorGull()
    {
         Pixel[][] pixels = this.getPixels2D(); 

         for (int row = 232; row < 319; row++) // left arm
         { 
           for (int col = 232; col < 343; col++)
           { 
               Pixel myPixel = pixels[row][col]; 
               Pixel newPixel = pixels[row][713-col];
               newPixel.setColor(myPixel.getColor());
            }
         } 
    }

    /** Method that mirrors the arms on the
     * snowman to make a snowman with 4 arms*/
    public void mirrorArms()
    {
         Pixel[][] pixels = this.getPixels2D(); 
      
         int averageR,averageG,averageB,numPixels; 
         int maxR,maxG,maxB,minR,minG,minB;
         
         averageR=averageG=averageB=numPixels=0;
         maxR =maxG=maxB=0;
         minR =minG=minB=255;
            
         // find the average/max/min RGB values in the arm area
         for (int row = 178; row < 182; row++) 
         { 
             for (int col = 161; col < 166; col++) 
             { 
                 numPixels++; 
                  
                 Pixel myPixel = pixels[row][col]; 
                  
                 averageR += myPixel.getRed(); 
                 averageG += myPixel.getGreen(); 
                 averageB += myPixel.getBlue(); 
                 
                 maxR = Math.max(maxR, myPixel.getRed()); 
                 maxG = Math.max(maxG, myPixel.getGreen());
                 maxB = Math.max(maxB, myPixel.getBlue());
                 minR = Math.min(minR, myPixel.getRed());
                 minG = Math.min(minG, myPixel.getGreen());
                 minB = Math.min(minB, myPixel.getBlue()); 
                  
             } 
         } 
          
         averageR = averageR / numPixels; 
         averageG = averageG / numPixels; 
         averageB = averageB / numPixels; 
          
         Color fishColor = new Color(averageR, averageG, averageB); 
          
         // calculates the distance 
         int redDistance = (maxR - minR); 
         int greenDistance = (maxG - minG); 
         int blueDistance = (maxB - minB); 
         
         // compute the coler ange on the arm area
         double range = Math.sqrt(redDistance * redDistance + 
                                  greenDistance * greenDistance + 
                                  blueDistance * blueDistance); 
 
         for (int row = 156; row < 200; row++) // left arm
         { 
           for (int col = 104; col < 169; col++)
           { 
               Pixel myPixel = pixels[row][col]; 
               // if color at this Pixel close to the arm color, make the pixel in the lower location the same color
               if (pixels[row][col].colorDistance(fishColor) < range) // if color close to the fish color, make it more blue
               { 
                        Pixel newArmPixela = pixels[row+69][col-6];
                        newArmPixela.setColor(myPixel.getColor()); //myPixel.getBlue() + 80);
                } 
            }
            for (int col = 234; col < 295; col++) // right arm
            { 
               Pixel myPixel = pixels[row][col]; 
               // if color at this Pixel close to the arm color, make the pixel in the lower location the same color
               if (pixels[row][col].colorDistance(fishColor) < range)
               { 
                        Pixel newArmPixelb = pixels[row+69][col+6];
                        newArmPixelb.setColor(myPixel.getColor());
                } 
            } 
         } 
    }
    /** Mirror just part of a picture of a temple */
    public void mirrorTemple()
    {
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();

        // loop through the rows
        for (int row = 27; row < 97; row++)
        {
            // loop from 13 to just before the mirror point
            for (int col = 13; col < mirrorPoint; col++)
            {

                leftPixel = pixels[row][col];      
                rightPixel = pixels[row]                       
                [mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /** copy from the passed fromPic to the
     * specified startRow and startCol in the
     * current picture
     * @param fromPic the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     */
    public void copy(Picture fromPic, 
    int startRow, int startCol)
    {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow; 
        fromRow < fromPixels.length &&
        toRow < toPixels.length; 
        fromRow++, toRow++)
        {
            for (int fromCol = 0, toCol = startCol; 
            fromCol < fromPixels[0].length &&
            toCol < toPixels[0].length;  
            fromCol++, toCol++)
            {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }   
    }

    /** Method to create a collage of several pictures */
    public void createCollage()
    {
        Picture flower1 = new Picture("flower1.jpg");
        Picture flower2 = new Picture("flower2.jpg");
        this.copy(flower1,0,0);
        this.copy(flower2,100,0);
        this.copy(flower1,200,0);
        Picture flowerNoBlue = new Picture(flower2);
        flowerNoBlue.zeroBlue();
        this.copy(flowerNoBlue,300,0);
        this.copy(flower1,400,0);
        this.copy(flower2,500,0);
        this.mirrorVertical();
        this.write("collage.jpg");
    }

    /** Method to show large changes in color 
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection(int edgeDist)
    {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; 
            col < pixels[0].length-1; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][col+1];
                rightColor = rightPixel.getColor();
                if (leftPixel.colorDistance(rightColor) > 
                edgeDist)
                    leftPixel.setColor(Color.BLACK);
                else
                    leftPixel.setColor(Color.WHITE);
            }
        }
    }

    /* Main method for testing - each class in Java can have a main 
     * method 
     */
    public static void main(String[] args) 
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.zeroBlue();
        beach.explore();
    }

} // this } is the end of class Picture, put all new methods before this
