
package org.usfirst.frc.team3316.robot.commands.vision;

public class TargetVision 
{
	 public TargetReport report = new TargetReport();
		/*
		 * Class Attributes
		 */
	 /*
	
		ColorImage image;
		BinaryImage filteredImage;
		ParticleAnalysisReport [] horzReports;
		ParticleAnalysisReport [] vertReports;
		ParticleAnalysisReport [] reports;
		int horzTargetCount = 0, vertTargetCount = 0;

       
      
    //constants for color threshold filtering of the ColorImage to a BinaryImage
    final static int H_MIN = RobotConstants.getInt("H_MIN");
    final static int H_MAX = RobotConstants.getInt("H_MAX");
    final static int S_MIN = RobotConstants.getInt("S_MIN");
    final static int S_MAX = RobotConstants.getInt("S_MAX");
    final static int V_MIN = RobotConstants.getInt("V_MIN");
    final static int V_MAX = RobotConstants.getInt("V_MAX");
    
    final static int ZERO = 0; //for math changing modifications
    
    //particle filtering constants
    final static int AREA_MIN = RobotConstants.getInt("AREA_MIN"); //minimum number pixel area of a particle
    final static int MAX_PARTICLE_NUM = RobotConstants.getInt("MAX_PARTICLE_NUM");
                                           //number of biggest particles analyzed in the image
    final static int MAX_TARGET_REPORTS = 2; //RobotConstants.getInt("MAX_TARGET_REPORTS"); 
                                             //number of maximum vertical and horizontal reports
    final static double VERT_RATIO_MIN = .1; //vertical target minimum rectangularity ratio (width/height)
    final static double VERT_RATIO_MAX = .35; //vertical target maximum rectangularity ratio (width/height)
    final static double HORZ_RATIO_MIN = 2.5; //horizontal target minimum rectangularity ratio (width/height)
    final static double HORZ_RATIO_MAX = 5.5; //horizontal target maximum rectangularity ratio (width/height)
    final static double IMP_RATIO_MIN = 0.5; //important ratio minimum, explained in sortHotGoals
    final static double IMP_RATIO_MAX = 0.8; //important ratio maximum, explained in sortHotGoals
    
    //Distance and angle from targets calculations
    final static double TARGET_HEIGHT = 0.8128; //in meters
    final static double OFFSET = RobotConstants.get("OFFSET"); //Average distance that needs to be substracted from the calculated distance
    final static int Y_IMAGE_RES = 240; //= RobotConstants.getInt("Y_IMAGE_RES");
    final static double X_IMAGE_RES = Y_IMAGE_RES * 4/3;
    final static double VIEW_ANGLE_Y = RobotConstants.get("VIEW_ANGLE_Y"); //was calculated for best distance results
    final static double VIEW_ANGLE_X = 57; //the camera's specifications

    static int isFirstRoundPassed = 0;
    
    public TargetVision () {}
    
    public void process() throws AxisCameraException, NIVisionException
    {
        //initialization of the attributes 
        image = null;
        filteredImage = null;
        horzReports = null;
        vertReports = null;
        reports = null;
        horzTargetCount = 0; vertTargetCount = 0;
        report.leftHot = false;
        report.rightHot = false;
        report.leftAngle = report.rightAngle = report.leftDistance = 
                report.rightDistance = RobotConstants.get("ErrorCode");
        
        //image processing
        getImage(); //gets the image from the camera
        filterImage(); //filters the image from a ColorImage to a BinaryImage based on a color threshold
        freeImage(); //frees the ColorImage from memory
        sortParticles(); //sorts the particles in the BinaryImage into vertical and horizontal reports
        freeFilteredImage(); //frees the BinaryImage
        sortHotGoals(); //determines hot goals based on the vertical and horizontal reports
        computeReport(); //calculates left distance and right distance and
                         //calculates angles from left target and right target
    }
    
    public void saveImage() throws NIVisionException
    {   
        if (image != null)
        {
            image.write("/Image.jpg");
        }
        else
        {
            RobotLog.log("tried to save  Image was null");
        }
    }
    public void saveFilteredImage () throws NIVisionException
    {
        if (filteredImage != null)
        {
            filteredImage.write("/FilteredImage.jpg");
        }
        else
        {
            RobotLog.log("tried to save but FilteredImage was null");
        }
    }
    
    void getImage () throws NIVisionException, AxisCameraException
    {
        image = camera.getImage();
        //image = new RGBImage("/testImage.jpg"); //for taking an image from the crio
    }
    
    void filterImage () throws NIVisionException
    {
        
        filteredImage = image.thresholdHSI(
                RobotConstants.getInt("H_MIN"), 
                RobotConstants.getInt("H_MAX"),
                RobotConstants.getInt("S_MIN"), 
                RobotConstants.getInt("S_MAX"), 
                RobotConstants.getInt("V_MIN"), 
                RobotConstants.getInt("V_MAX"));
        saveFilteredImage();
        if (isFirstRoundPassed == 0)
        {
            isFirstRoundPassed++;
            saveImage();
            saveFilteredImage();
        }
    }
    
    void sortParticles () throws NIVisionException
    {
        int particleNum = filteredImage.getNumberParticles();
        reports = filteredImage.getOrderedParticleAnalysisReports(Math.min (MAX_PARTICLE_NUM, particleNum));
        vertReports = new ParticleAnalysisReport[MAX_TARGET_REPORTS];
        horzReports = new ParticleAnalysisReport[MAX_TARGET_REPORTS];
        
        
        for (int i = 0; i < reports.length; i++)
        {
            if (reports[i] != null)
            {
                double ratio = ((double) reports[i].boundingRectWidth)/((double) reports[i].boundingRectHeight);
                double area = reports[i].particleArea;
                
                if (area <= AREA_MIN) 
                {
                    continue;
                }
                else if (ratio < VERT_RATIO_MAX && ratio > VERT_RATIO_MIN && vertTargetCount < MAX_TARGET_REPORTS) //cr out of bounds error
                {
                    vertReports[vertTargetCount++] = reports[i];
                    
                }
                else if (ratio < HORZ_RATIO_MAX && ratio > HORZ_RATIO_MIN && horzTargetCount < MAX_TARGET_REPORTS)
                {
                    horzReports[horzTargetCount++] = reports[i];
                    
                }
            }
        }
    }
    
    void sortHotGoals () throws NIVisionException
    {
        for (int i = 0; i < vertReports.length; i++)
        {
            for (int j = 0; j < horzReports.length; j++)
            {
                if (vertReports[i] != null && horzReports[j] != null)
                {
                    
                    double importantRatio = ((double)horzReports[j].center_mass_x - horzReports[j].boundingRectLeft)
                            /(horzReports[j].center_mass_x - vertReports[i].center_mass_x);
                    
                    if (importantRatio > IMP_RATIO_MIN && importantRatio < IMP_RATIO_MAX)
                    {
                        report.rightHot = true;
                        break;
                    }
                    else if (importantRatio < -IMP_RATIO_MIN && importantRatio > -IMP_RATIO_MAX)
                    {
                        report.leftHot = true;
                        break;
                    }
                }
            }
        }
    }
    
    void computeReport () throws NIVisionException
    {
        if (vertTargetCount == 2)
        {
            if (vertReports[0].center_mass_x < vertReports[1].center_mass_x)
            {
                report.leftDistance = computeDistance(vertReports[0]);
                report.rightDistance = computeDistance(vertReports[1]);
                report.leftAngle = computeAngleFromTarget(vertReports[0]);
                report.rightAngle = computeAngleFromTarget(vertReports[1]);
            }
            else
            {
                report.leftDistance = computeDistance(vertReports[1]);
                report.rightDistance = computeDistance(vertReports[0]);
                report.leftAngle = computeAngleFromTarget(vertReports[1]);
                report.rightAngle = computeAngleFromTarget(vertReports[0]);
            }
        }
        else if (vertTargetCount == 1)
        {
            if (report.leftHot)
            {
                report.leftDistance = computeDistance(vertReports[0]);
                report.leftAngle = computeAngleFromTarget(vertReports[0]);
            }
            else if (report.rightHot)
            {
                report.rightDistance = computeDistance(vertReports[0]);
                report.rightAngle = computeAngleFromTarget(vertReports[0]);
            }
            else
            {
                report.leftDistance = computeDistance(vertReports[0]);
                report.leftAngle = computeAngleFromTarget(vertReports[0]);
            }
        }
    }
    
    public void freeImage () throws NIVisionException
    {
        image.free();
    }
    
    public void freeFilteredImage () throws  NIVisionException
    {
        filteredImage.free();
    }
                        
    public double computeDistance (ParticleAnalysisReport report) throws NIVisionException 
    {
        double height;
        height = report.boundingRectHeight; //in meters
        return (Y_IMAGE_RES * TARGET_HEIGHT / (height * 2 * Math.tan(VIEW_ANGLE_Y*Math.PI/(360)))) - OFFSET;
    }
    
    double computeAngleFromTarget (ParticleAnalysisReport report)
    {
        double xDiff = (report.center_mass_x - X_IMAGE_RES / 2);
        double angleToReturn = (xDiff * VIEW_ANGLE_X) / X_IMAGE_RES;
        return angleToReturn;
    } 
    */
    
}
