import javax.swing.*;
import java.awt.*;
import java.lang.Math;


public class Grid extends JPanel{
    private final int cellColumnCount = 250;   //125
    private final int cellRowCount = 125;  //80
    public final int cellWidth = 3; //7
    public final int cellHeight = 3; //7

    public GridCell[][] grid = new GridCell[cellColumnCount][cellRowCount];

    private final Color landGreen = new Color(0, 183, 0);
    private final Color oceanBlue = new Color(16, 0, 204);
    private final Color freshBlue = new Color(0, 0, 255);
    private final Color mountainWhite = new Color(221,221,221);
    private final Color tropicalRainforest = new Color(0, 96, 6);
    private final Color tropicalSeasonalRain = new Color(0, 255, 128);
    private final Color temperateRainforest = new Color(0, 130, 95);
    private final Color temperateDeciduous = new Color(0, 204, 0);
    private final Color temperateGrassland = new Color(114, 255, 0);
    private final Color desert = new Color(255, 67, 0);
    private final Color savanna = new Color(255, 199, 0);
    private final Color tundra = new Color(0, 255, 255);
    private final Color taiga = new Color(0, 170, 255);

    private double globalAvgHigh;
    private double globalAvgLow;
    private double globalMaxHigh;
    private double globalMaxLow;
    private double globalMinHigh;
    private double globalMinLow;

    private boolean biomeFlag = false;
    private boolean maxTempFlag = false;
    private boolean minTempFlag = false;
    private boolean maxRangeFlag = false;
    private boolean earthFlag = false;
    private boolean precipFlag = false;
    private boolean avgTempFlag = false;
    private String windComesFrom;

    public Grid() {
        setLayout(null);
        for (int c = 0; c < cellColumnCount; c++) {
            for (int r = 0; r < cellRowCount; r++) {
                grid[c][r] = new GridCell();
                grid[c][r].setX(c * cellWidth);
                grid[c][r].setY(r * cellHeight);
                //System.out.println("X: " + grid[c][r].getX() + " Y: " + grid[c][r].getY());
            }
        }
        defineNeighbors();
    }

    public void defineNeighbors() {
        for (int c = 0; c < cellColumnCount; c++) {
            for (int r = 0; r < cellRowCount; r++) {
                switch (c) {
                    case 0:
                        grid[c][r].addNeighbor(grid[c + 1][r]);
                        grid[c][r].addNeighbor(grid[cellColumnCount - 1][r]);
                        break;
                    case (cellColumnCount - 1):
                        grid[c][r].addNeighbor(grid[c - 1][r]);
                        grid[c][r].addNeighbor(grid[0][r]);
                        break;
                    default:
                        grid[c][r].addNeighbor(grid[c - 1][r]);
                        grid[c][r].addNeighbor(grid[c + 1][r]);
                }
                switch (r) {
                    case 0:
                        grid[c][r].addNeighbor(grid[c][r + 1]);
                        break;
                    case (cellRowCount - 1):
                        grid[c][r].addNeighbor(grid[c][r - 1]);
                        break;
                    default:
                        grid[c][r].addNeighbor(grid[c][r - 1]);
                        grid[c][r].addNeighbor(grid[c][r + 1]);
                }
            }
        }
    }

    public void islandMaker(double size, double variation, double trimEdge, double poleDistance) {
        double point12x = 0, point23x = 0, point34x = 0, point41x = 0;
        double m = 0, m1 = 0, m2 = 0, m3 = 0, m4 = 0, m5 = 0, m6 = 0, m7 = 0;
        double b = 0, b1 = 0, b2 = 0, b3 = 0, b4 = 0, b5 = 0, b6 = 0, b7 = 0;
        double m0 = 0, m30 = 0, m40 = 0, m70 = 0;
        double b0 = 0, b30 = 0, b40 = 0, b70 = 0;
        double mZ = 0, m3Z = 0, m4Z = 0, m7Z = 0;
        double bZ = 0, bZ1 = 0, b3Z = 0, b3Z1 = 0, b4Z = 0, b4Z1 = 0, b7Z = 0, b7Z1 = 0;
        int centerX = (int) Math.floor(Math.random() * cellColumnCount);              //picks central starting point
        int centerY = (int) Math.floor(Math.random() * (cellRowCount - poleDistance) + (poleDistance / 2));         //makes sure point not too close to poles
        grid[centerX][centerY].changeCellColor(landGreen);

        //below establishes the points which form the outline of our island
        //to visualize the rough location of the points on a clock, point 1 is at 12 o clock, point12 at 1:30, point2 at 3, point23 at 4:30, point3 at 6, point34 at 7:30, point4 at 9 and point41 at 10:30
        double point1y = centerY - (Math.floor(Math.random() * size) + size);   //picks point 20-40 units away
        if (point1y < 0) {
            point1y = 0;
        }          //makes sure to pick a viable point within the map
        double point1x = centerX + ((Math.floor(Math.random() * (size / 2))) * (Math.floor(Math.random() * 2) == 1 ? 1 : -1));  //moves point to one side or another slightly for variation
        if (point1x < 0) {
            point1x = 0;
        }  //this and below makes sure that X point doesn't go off one end of the map and fuck up future code
        else if (point1x >= cellColumnCount) {
            point1x = (cellColumnCount - 1);
        }

        double point3y = (centerY + (Math.floor(Math.random() * size) + size));
        if (point3y >= (cellRowCount)) {
            point3y = cellRowCount - 1;
        }
        double point3x = (centerX + ((Math.floor(Math.random() * (size / 2))) * (Math.floor(Math.random() * 2) == 1 ? 1 : -1)));
        if (point3x < 0) {
            point3x = 0;
        } else if (point3x >= cellColumnCount) {
            point3x = (cellColumnCount - 1);
        }

        double point2x = (centerX + (Math.floor(Math.random() * size) + size));
        if (point2x >= cellColumnCount) {
            point2x = (((point2x - centerX) + (cellColumnCount - (cellColumnCount - centerX)))) % cellColumnCount;
        }  //if point2x goes off the map one way or another, this will loop it around to the other side
        double point2y = (centerY + ((Math.floor(Math.random() * 10)) * (Math.floor(Math.random() * 2) == 1 ? 1 : -1)));
        if (point2y < (point1y + variation)) {
            point2y = (point1y + variation);
        }     //this is to make sure the island doesn't look quite so squished if the center points lands near the poles
        else if (point2y > (point3y - variation)) {
            point2y = (point3y - variation);
        }

        double point4x = (centerX - (Math.floor(Math.random() * size) + size));
        if (point4x < 0) {
            point4x = ((centerX + (centerX - point4x)) + (cellColumnCount - ((centerX - point4x) * 2)));
        }
        double point4y = (centerY + ((Math.floor(Math.random() * 10)) * (Math.floor(Math.random() * 2) == 1 ? 1 : -1)));
        if (point4y < (point1y + variation)) {
            point4y = (point1y + variation);
        } else if (point4y > (point3y - variation)) {
            point4y = (point3y - variation);
        }

        //FINDING MIDPOINTS BETWEEN POINTS 1, 2, 3, 4
        if (point2x > point1x) {
            point12x = (Math.floor((point1x + point2x) / 2));
        } else if (point2x < point1x) {
            point12x = (Math.floor((((cellColumnCount - Math.abs(point1x - point2x)) / 2) + (cellColumnCount - (cellColumnCount - point1x))) % cellColumnCount));
        }
        double difx = ((Math.max((Math.min((point12x - point1x), (point2x - point12x)) - 1), 0)) / 3);
        difx *= Math.floor(Math.random() * 2) == 1 ? 1.25 : -0.5;
        point12x += (Math.floor(Math.random() * difx));

        double point12y = (Math.floor((point2y + point1y) / 2));
        double dify = ((Math.max((Math.min((point2y - point12y), (point12y - point1y)) - 1), 0)) / 3);
        dify *= Math.floor(Math.random() * 2) == 1 ? 1.25 : -0.5;
        point12y += Math.floor(Math.random() * dify);

        if (point2x > point3x) {
            point23x = (Math.floor((point3x + point2x) / 2));
        } else if (point2x < point3x) {
            point23x = (Math.floor((((cellColumnCount - Math.abs(point3x - point2x)) / 2) + (cellColumnCount - (cellColumnCount - point3x))) % cellColumnCount));
        }
        double difx1 = ((Math.max((Math.min((point2x - point23x), (point23x - point3x)) - 1), 0)) / 3);
        difx1 *= (Math.floor(Math.random() * 2) == 1 && difx < 0) ? 1.25 : -0.5;
        point23x += (Math.floor(Math.random() * difx1));
        if ((int) point12x < 50 && (int) point23x > 75) {
            point23x = point12x;
        } else if ((int) point12x > 75 && (int) point23x < 50) {
            point23x = point12x;
        }

        double point23y = (Math.floor((point2y + point3y) / 2));
        double dify1 = ((Math.max((Math.min((point3y - point23y), (point23y - point2y)) - 1), 0)) / 3);
        dify1 *= (Math.floor(Math.random() * 2) == 1 && dify < 0) ? 1.25 : -0.5;
        point23y += Math.floor(Math.random() * dify1);

        if (point4x < point3x) {
            point34x = (Math.floor((point3x + point4x) / 2));
        } else if (point4x > point3x) {
            point34x = ((Math.floor((point3x + ((cellColumnCount - Math.abs(point3x - point4x)) / 2)) + (cellColumnCount - ((cellColumnCount - Math.abs(point3x - point4x)))))) % cellColumnCount);
        }

        double difx2 = ((Math.max((Math.min((point3x - point34x), (point34x - point4x)) - 1), 0)) / 3);
        difx2 *= (Math.floor(Math.random() * 2) == 1 && difx < 0 && difx1 < 0) ? 1.25 : -0.5;
        point34x += Math.floor(Math.random() * difx2);

        double point34y = (Math.floor((point4y + point3y) / 2));
        double dify2 = ((Math.max((Math.min((point3y - point34y), (point34y - point4y)) - 1), 0)) / 3);
        dify2 *= (Math.floor(Math.random() * 2) == 1 && dify < 0 && dify1 < 0) ? 1.25 : -0.5;
        point34y += Math.floor(Math.random() * dify2);

        if (point4x < point1x) {
            point41x = (Math.floor((point1x + point4x) / 2));
        } else if (point4x > point1x) {
            point41x = ((Math.floor((point1x + ((cellColumnCount - Math.abs(point1x - point4x)) / 2)) + (cellColumnCount - ((cellColumnCount - Math.abs(point1x - point4x)))))) % cellColumnCount);
        }

        double difx3 = ((Math.max((Math.min((point1x - point41x), (point41x - point4x)) - 1), 0)) / 3);
        difx3 *= (Math.floor(Math.random() * 2) == 1 && difx < 0 && difx1 < 0 && difx2 < 0) ? 1.25 : -0.5;
        point41x += Math.floor(Math.random() * difx3);
        //System.out.println("Point34x: " + point34x + " Point41x: " + point41x);
        if ((int) point34x < 50 && (int) point41x > 75) {
            point41x = point34x;
        } else if ((int) point34x > 75 && (int) point41x < 50) {
            point41x = point34x;
        }

        double point41y = (Math.floor((point4y + point1y) / 2));
        double dify3 = ((Math.max((Math.min((point4y - point41y), (point41y - point1y)) - 1), 0)) / 3);
        dify3 *= (Math.floor(Math.random() * 2) == 1 && dify < 0 && dify1 < 0 && dify2 < 0) ? 1.25 : -0.5;
        point41y += Math.floor(Math.random() * dify3);

        //SLOPES(M) AND INTERSECTIONS(B)
        //Upper Half
        if (point4x < point1x || point4x < point41x) {
            m = (point4y - point41y) / (point4x - point41x);
            b = point41y - (m * point41x);
            if (point4x < point41x && point4x > point1x) {
                m0 = (point1y - point41y) / (cellColumnCount - Math.abs(point41x - point1x));
                b0 = (point1y + 2) - (m0 * point1x);
            }
        } else if (point4x > point1x) {
            mZ = (point41y - point4y) / (cellColumnCount - Math.abs(point4x - point41x));
            bZ = point4y - (mZ * point4x);
            bZ1 = point41y - (mZ * point41x);
        }

        if (point41x < point1x) {
            m1 = (point41y - point1y) / (point41x - point1x);
            b1 = point1y - (m1 * point1x);
        } else if (point41x > point1x) {
            m1 = (point1y - point41y) / (cellColumnCount - Math.abs(point41x - point1x));
            b1 = point41y - (m1 * point41x);
        }

        if (point12x > point1x) {
            m2 = (point1y - point12y) / (point1x - point12x);
            b2 = point12y - (m2 * point12x);
        } else if (point12x < point1x) {
            m2 = (point12y - point1y) / (cellColumnCount - Math.abs(point1x - point12x));
            b2 = point12y - (m2 * point12x);
        }

        if (point2x > point1x || point2x > point12x) {
            m3 = (point12y - point2y) / (point12x - point2x);
            b3 = point2y - (m3 * point2x);
            if (point2x > point12x && point2x < point1x) {
                m30 = (point12y - point1y) / (cellColumnCount - Math.abs(point12x - point1x));
                b30 = (point1y + 2) - (m30 * point1x);
            }
        } else if (point2x < point1x) {
            m3Z = (point2y - point12y) / (cellColumnCount - Math.abs(point2x - point12x));
            b3Z = point2y - (m3Z * point2x);
            b3Z1 = point12y - (m3Z * point12x);
        }

        //Lower Half
        if (point4x < point3x || point4x < point34x) {
            m4 = (point4y - point34y) / (point4x - point34x);
            b4 = point34y - (m4 * point34x);
            if (point4x < point34x && point4x > point3x) {
                m40 = (point3y - point34y) / (cellColumnCount - Math.abs(point3x - point34x));
                b40 = (point3y - 2) - (m40 * point3x);
            }
        } else if (point4x > point3x) {
            m4Z = (point34y - point4y) / (cellColumnCount - Math.abs(point4x - point34x));
            b4Z = point4y - (m4Z * point4x);
            b4Z1 = point34y - (m4Z * point34x);
        }

        if (point34x < point3x) {
            m5 = (point34y - point3y) / (point34x - point3x);
            b5 = point3y - (m5 * point3x);
        } else if (point34x > point3x) {
            m5 = (point3y - point34y) / (cellColumnCount - Math.abs(point34x - point3x));
            b5 = point34y - (m5 * point34x);
        }

        if (point23x > point3x) {
            m6 = (point3y - point23y) / (point3x - point23x);
            b6 = point23y - (m6 * point23x);
        } else if (point23x < point3x) {
            m6 = (point23y - point3y) / (cellColumnCount - Math.abs(point23x - point3x));
            b6 = point23y - (m6 * point23x);
        }

        if (point2x > point3x || point2x > point23x) {
            m7 = (point23y - point2y) / (point23x - point2x);
            b7 = point2y - (m7 * point2x);
            if (point2x > point23x && point2x < point3x) {
                m70 = (point23y - point3y) / (cellColumnCount - Math.abs(point23x - point3x));
                b70 = (point3y - 2) - (m70 * point3x);
            }
        } else if (point2x < point3x) {
            m7Z = (point2y - point23y) / (cellColumnCount - Math.abs(point2x - point23x));
            b7Z = point2y - (m7Z * point2x);
            b7Z1 = point23y - (m7Z * point23x);
        }

        for (int c = 0; c < cellColumnCount; c++) {
            for (int r = 0; r < cellRowCount; r++) {
                //Upper Half
                if (point4x < point1x || point4x < point41x) {
                    if (c > (point4x + trimEdge) && c <= point41x && r <= point4y && r > (m * c) + b) {
                        grid[c][r].changeCellColor(landGreen);
                    } else if (point4x < point41x && point4x > point1x && c <= point1x && r <= point4y && r > (m0 * c) + b0) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                } else if (point4x > point1x) {
                    if (c > (point4x + trimEdge) && r <= point4y && r > (mZ * c) + bZ) {
                        grid[c][r].changeCellColor(landGreen);
                    } else if (c <= point41x && r <= point4y && r > (mZ * c) + bZ1) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                }

                if (point41x < point1x) {    //MOD
                    if (c >= point41x && c <= point1x && r <= Math.max(point4y, point2y) && (point4x > point3x ? r < (m4Z * c) + b4Z1 : r < (m4 * c) + b4) && r < (m5 * c) + b5 && r > (m1 * c) + b1 && r > (point1y + trimEdge)) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                } else if (point41x > point1x) {
                    if (c >= point41x && r <= point4y && r > (m1 * c) + b1 && r > (point1y + trimEdge)) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                }

                if (point12x > point1x) {   //MOD
                    if (c >= point1x && c <= point12x && r <= Math.max(point2y, point4y) && r < (m6 * c) + b6 && (point2x < point3x ? r < (m7Z * c) + b7Z1 : r < (m7 * c) + b7) && r > (m2 * c) + b2 && r > (point1y + trimEdge)) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                } else if (point12x < point1x) {
                    if (c <= point12x && r <= point2y && r > (m2 * c) + b2 && r > (point1y + trimEdge)) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                }

                if (point2x > point1x || point2x > point12x) {
                    if (c >= point12x && c < (point2x - trimEdge) && r <= point2y && r > (m3 * c) + b3) {
                        grid[c][r].changeCellColor(landGreen);
                    } else if (point2x > point12x && point2x < point1x && c >= point1x && r <= point2y && r > (m30 * c) + b30) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                } else if (point2x < point1x) {
                    if (c < (point2x + trimEdge) && r <= point2y && r > (m3Z * c) + b3Z) {
                        grid[c][r].changeCellColor(landGreen);
                    } else if (c >= point12x && r <= point2y && r > (m3Z * c) + b3Z1) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                }
                //Lower Half
                if (point4x < point3x || point4x < point34x) {
                    if (c > (point4x + trimEdge) && c <= point34x && r >= point4y && r < (m4 * c) + b4) {
                        grid[c][r].changeCellColor(landGreen);
                    } else if (point4x < point34x && point4x > point3x && c <= point3x && r >= point4y && r < (m40 * c) + b40) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                } else if (point4x > point3x) {
                    if (c > (point4x + trimEdge) && r >= point4y && r < (m4Z * c) + b4Z) {
                        grid[c][r].changeCellColor(landGreen);
                    } else if (c <= point34x && r >= point4y && r < (m4Z * c) + b4Z1) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                }

                if (point34x < point3x) {  //MOD
                    if (c >= point34x && c <= point3x && r >= Math.min(point4y, point2y) && (point4x > point1x ? r > (mZ * c) + bZ1 : r > (m * c) + b) && r > (m1 * c) + b1 && r < (m5 * c) + b5 && r < (point3y - trimEdge)) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                } else if (point34x > point3x) {
                    if (c >= point34x && r >= point4y && r < (m5 * c) + b5 && r > (point1y + trimEdge)) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                }


                if (point23x > point3x) {    //MOD
                    if (c >= point3x && c <= point23x && r >= Math.min(point2y, point4y) && (point2x < point1x ? r > (m3Z * c) + b3Z1 : r > (m3 * c) + b3) && r > (m2 * c) + b2 && r < (m6 * c) + b6 && r < (point3y - trimEdge)) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                } else if (point23x < point3x) {
                    if (c <= point23x && r >= point2y && r < (m6 * c) + b6 && r > (point1y + trimEdge)) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                }

                if (point2x > point3x || point2x > point23x) {
                    if (c >= point23x && c < (point2x - trimEdge) && r >= point2y && r < (m7 * c) + b7) {
                        grid[c][r].changeCellColor(landGreen);
                    } else if (point2x > point23x && point2x < point3x && c >= point3x && r >= point2y && r < (m70 * c) + b70) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                } else if (point2x < point3x) {
                    if (c < (point2x + trimEdge) && r >= point2y && r < (m7Z * c) + b7Z) {
                        grid[c][r].changeCellColor(landGreen);
                    } else if (c >= point23x && r >= point2y && r < (m7Z * c) + b7Z1) {
                        grid[c][r].changeCellColor(landGreen);
                    }
                }

            }
        }
    }

    public void islandPlacer() {
        //until you go back and figure out what exactly poleDistance does, just keep it same size as "size"
        //trimEdge - always 2 when size is 10+. 1 when below. Actually caps at size 3 when you get to 30+.
        //what the hell does variation do?
       // islandMaker(20, 5, 2, 20); //1 size 20
        //islandMaker(20, 20, 2, 20); //1 size 20
        islandMaker(30, 8, 3, 30); //1 size 20
        //islandMaker(40, 8, 4, 40); //1 size 20
        //islandMaker(10, 5, 1, 10); //1 size 20
        /*islandMaker(15, 5, 2, 15);  //1
        islandMaker(8, 4, 1, 8); //4*/

        double landPercent = 0;
        /*while (landPercent < 0.4) {
            int land = 0;
            for (int c = 0; c < cellColumnCount; c++) {
                for (int r = 0; r < cellRowCount; r++) {
                    if (grid[c][r].color == landGreen) {
                        land++;
                    }
                }
            }
            landPercent = ((double) land) / ((double)(cellColumnCount * cellRowCount));
            System.out.println("PercentLand: " + landPercent + " Land: " + land);
            if (landPercent < 0.4) {
                double startingSize = ((Math.random() * 4) + 4);
                islandMaker(startingSize, 2, 1, 6);
            }
        }*/
        repaint();
    }

    public void lakeMaker(double variation, double trimEdge) {
        //for (int lake = 0; lake < 100; lake++) {
        boolean lakePlaced = false;
        while(lakePlaced == false) {
            int lakeX = (int) (Math.floor(Math.random() * (cellColumnCount)));
            int lakeY = (int) (Math.floor(Math.random() * (cellRowCount - 10) + 5));
            boolean tooCloseN = false, tooCloseNE = false, tooCloseE = false, tooCloseSE = false, tooCloseS = false, tooCloseSW = false, tooCloseW = false, tooCloseNW = false;
            int Ndist = 0, NEdist = 0, Edist = 0, SEdist = 0, Sdist = 0, SWdist = 0, Wdist = 0, NWdist = 0;
            for (int i = 0; i < 10; i++) {
                Ndist--;
                if (grid[lakeX][(cellRowCount + lakeY + Ndist) % cellRowCount].color.equals(oceanBlue)) {
                    tooCloseN = true;
                }
            }
            for (int i = 0; i < 10; i++) {
                Wdist--;
                if (grid[(cellColumnCount + (lakeX + Wdist)) % cellColumnCount][lakeY].color.equals(oceanBlue)) {
                    tooCloseW = true;
                }
            }
            for (int i = 0; i < 10; i++) {
                Sdist++;
                if (grid[lakeX][(lakeY + Sdist) % cellRowCount].color.equals(oceanBlue)) {
                    tooCloseS = true;
                }
            }
            for (int i = 0; i < 10; i++) {
                Edist++;
                if (grid[(lakeX + Edist) % cellColumnCount][lakeY].color.equals(oceanBlue)) {
                    tooCloseE = true;
                }
            }
            for (int i = 0; i < 7; i++) {
                NWdist++;
                if (grid[(cellColumnCount + lakeX - NWdist) % cellColumnCount][(cellRowCount + lakeY - NWdist) % cellRowCount].color.equals(oceanBlue)) {
                    tooCloseNW = true;
                }
            }
            for (int i = 0; i < 7; i++) {
                SWdist++;
                if (grid[(cellColumnCount + lakeX - SWdist) % cellColumnCount][(lakeY + SWdist) % cellRowCount].color.equals(oceanBlue)) {
                    tooCloseSW = true;
                }
            }
            for (int i = 0; i < 7; i++) {
                SEdist++;
                if (grid[(lakeX + SEdist) % cellColumnCount][(lakeY + SEdist) % cellRowCount].color.equals(oceanBlue)) {
                    tooCloseSE = true;
                }
            }
            for (int i = 0; i < 7; i++) {
                NEdist++;
                if (grid[(lakeX + NEdist) % cellColumnCount][(cellRowCount + lakeY - NEdist) % cellRowCount].color.equals(oceanBlue)) {
                    tooCloseNE = true;
                }
            }
            if (tooCloseN == false && tooCloseS == false && tooCloseE == false && tooCloseW == false && tooCloseNE == false && tooCloseNW == false && tooCloseSE == false && tooCloseSW == false) {
                double point12x = 0, point23x = 0, point34x = 0, point41x = 0;
                double m = 0, m1 = 0, m2 = 0, m3 = 0, m4 = 0, m5 = 0, m6 = 0, m7 = 0;
                double b = 0, b1 = 0, b2 = 0, b3 = 0, b4 = 0, b5 = 0, b6 = 0, b7 = 0;
                double m0 = 0, m30 = 0, m40 = 0, m70 = 0;
                double b0 = 0, b30 = 0, b40 = 0, b70 = 0;
                double mZ = 0, m3Z = 0, m4Z = 0, m7Z = 0;
                double bZ = 0, bZ1 = 0, b3Z = 0, b3Z1 = 0, b4Z = 0, b4Z1 = 0, b7Z = 0, b7Z1 = 0;

                int centerX = lakeX;
                int centerY = lakeY;
                grid[centerX][centerY].color = freshBlue;

                //below establishes the points which form the outline of our island
                //to visualize the rough location of the points on a clock, point 1 is at 12 o clock, point12 at 1:30, point2 at 3, point23 at 4:30, point3 at 6, point34 at 7:30, point4 at 9 and point41 at 10:30
                double point1y = centerY - (Math.floor(Math.random() * 1) + 4);   //picks point 20-40 units away
                if (point1y < 0) {
                    point1y = 0;
                }          //makes sure to pick a viable point within the map
                double point1x = centerX + ((Math.floor(Math.random() * (2))) * (Math.floor(Math.random() * 2) == 1 ? 1 : -1));  //moves point to one side or another slightly for variation
                if (point1x < 0) {
                    point1x = 0;
                }  //this and below makes sure that X point doesn't go off one end of the map and fuck up future code
                else if (point1x >= cellColumnCount) {
                    point1x = (cellColumnCount - 1);
                }

                double point3y = (centerY + (Math.floor(Math.random() * 1) + 4));
                if (point3y >= (cellRowCount)) {
                    point3y = cellRowCount - 1;
                }
                double point3x = (centerX + ((Math.floor(Math.random() * (2))) * (Math.floor(Math.random() * 2) == 1 ? 1 : -1)));
                if (point3x < 0) {
                    point3x = 0;
                } else if (point3x >= cellColumnCount) {
                    point3x = (cellColumnCount - 1);
                }

                double point2x = (centerX + (Math.floor(Math.random() * 1) + 4));
                if (point2x >= cellColumnCount) {
                    point2x = (((point2x - centerX) + (cellColumnCount - (cellColumnCount - centerX)))) % cellColumnCount;
                }  //if point2x goes off the map one way or another, this will loop it around to the other side
                double point2y = (centerY + ((Math.floor(Math.random() * 10)) * (Math.floor(Math.random() * 2) == 1 ? 1 : -1)));
                if (point2y < (point1y + variation)) {
                    point2y = (point1y + variation);
                }     //this is to make sure the island doesn't look quite so squished if the center points lands near the poles
                else if (point2y > (point3y - variation)) {
                    point2y = (point3y - variation);
                }

                double point4x = (centerX - (Math.floor(Math.random() * 1) + 4));
                if (point4x < 0) {
                    point4x = ((centerX + (centerX - point4x)) + (cellColumnCount - ((centerX - point4x) * 2)));
                }
                double point4y = (centerY + ((Math.floor(Math.random() * 10)) * (Math.floor(Math.random() * 2) == 1 ? 1 : -1)));
                if (point4y < (point1y + variation)) {
                    point4y = (point1y + variation);
                } else if (point4y > (point3y - variation)) {
                    point4y = (point3y - variation);
                }

                //console.log("CenterX: " + centerX + " CenterY: " + centerY);
                //console.log("clonex " + cloneCenterX + " cloney " + cloneCenterY);
                //console.log("point1x " + point1x + " point3x " + point3x + " point2y " + point2y + " point4y " + point4y);

                //FINDING MIDPOINTS BETWEEN POINTS 1, 2, 3, 4
                if (point2x > point1x) {
                    point12x = (Math.floor((point1x + point2x) / 2));
                } else if (point2x < point1x) {
                    point12x = (Math.floor((((cellColumnCount - Math.abs(point1x - point2x)) / 2) + (cellColumnCount - (cellColumnCount - point1x))) % cellColumnCount));
                }
                double difx = ((Math.max((Math.min((point12x - point1x), (point2x - point12x)) - 1), 0)) / 3);
                difx *= Math.floor(Math.random() * 2) == 1 ? 1.25 : -0.5;
                point12x += (Math.floor(Math.random() * difx));

                double point12y = (Math.floor((point2y + point1y) / 2));
                double dify = ((Math.max((Math.min((point2y - point12y), (point12y - point1y)) - 1), 0)) / 3);
                dify *= Math.floor(Math.random() * 2) == 1 ? 1.25 : -0.5;
                point12y += Math.floor(Math.random() * dify);

                if (point2x > point3x) {
                    point23x = (Math.floor((point3x + point2x) / 2));
                } else if (point2x < point3x) {
                    point23x = (Math.floor((((cellColumnCount - Math.abs(point3x - point2x)) / 2) + (cellColumnCount - (cellColumnCount - point3x))) % cellColumnCount));
                }
                double difx1 = ((Math.max((Math.min((point2x - point23x), (point23x - point3x)) - 1), 0)) / 3);
                difx1 *= (Math.floor(Math.random() * 2) == 1 && difx < 0) ? 1.25 : -0.5;
                point23x += (Math.floor(Math.random() * difx1));
                //System.out.println("Point12x: " + point12x + " Point23x: " + point23x);
                if ((int) point12x < 50 && (int) point23x > 75) {
                    point23x = point12x;
                } else if ((int) point12x > 75 && (int) point23x < 50) {
                    point23x = point12x;
                }

                double point23y = (Math.floor((point2y + point3y) / 2));
                double dify1 = ((Math.max((Math.min((point3y - point23y), (point23y - point2y)) - 1), 0)) / 3);
                dify1 *= (Math.floor(Math.random() * 2) == 1 && dify < 0) ? 1.25 : -0.5;
                point23y += Math.floor(Math.random() * dify1);

                if (point4x < point3x) {
                    point34x = (Math.floor((point3x + point4x) / 2));
                } else if (point4x > point3x) {
                    point34x = ((Math.floor((point3x + ((cellColumnCount - Math.abs(point3x - point4x)) / 2)) + (cellColumnCount - ((cellColumnCount - Math.abs(point3x - point4x)))))) % cellColumnCount);
                }

                double difx2 = ((Math.max((Math.min((point3x - point34x), (point34x - point4x)) - 1), 0)) / 3);
                difx2 *= (Math.floor(Math.random() * 2) == 1 && difx < 0 && difx1 < 0) ? 1.25 : -0.5;
                point34x += Math.floor(Math.random() * difx2);

                double point34y = (Math.floor((point4y + point3y) / 2));
                double dify2 = ((Math.max((Math.min((point3y - point34y), (point34y - point4y)) - 1), 0)) / 3);
                dify2 *= (Math.floor(Math.random() * 2) == 1 && dify < 0 && dify1 < 0) ? 1.25 : -0.5;
                point34y += Math.floor(Math.random() * dify2);

                if (point4x < point1x) {
                    point41x = (Math.floor((point1x + point4x) / 2));
                } else if (point4x > point1x) {
                    point41x = ((Math.floor((point1x + ((cellColumnCount - Math.abs(point1x - point4x)) / 2)) + (cellColumnCount - ((cellColumnCount - Math.abs(point1x - point4x)))))) % cellColumnCount);
                }

                double difx3 = ((Math.max((Math.min((point1x - point41x), (point41x - point4x)) - 1), 0)) / 3);
                difx3 *= (Math.floor(Math.random() * 2) == 1 && difx < 0 && difx1 < 0 && difx2 < 0) ? 1.25 : -0.5;
                point41x += Math.floor(Math.random() * difx3);
                //System.out.println("Point34x: " + point34x + " Point41x: " + point41x);
                if ((int) point34x < 50 && (int) point41x > 75) {
                    point41x = point34x;
                } else if ((int) point34x > 75 && (int) point41x < 50) {
                    point41x = point34x;
                }

                double point41y = (Math.floor((point4y + point1y) / 2));
                double dify3 = ((Math.max((Math.min((point4y - point41y), (point41y - point1y)) - 1), 0)) / 3);
                dify3 *= (Math.floor(Math.random() * 2) == 1 && dify < 0 && dify1 < 0 && dify2 < 0) ? 1.25 : -0.5;
                point41y += Math.floor(Math.random() * dify3);

                //SLOPES(M) AND INTERSECTIONS(B)
                //Upper Half
                if (point4x < point1x || point4x < point41x) {
                    m = (point4y - point41y) / (point4x - point41x);
                    b = point41y - (m * point41x);
                    if (point4x < point41x && point4x > point1x) {
                        m0 = (point1y - point41y) / (cellColumnCount - Math.abs(point41x - point1x));
                        b0 = (point1y + 2) - (m0 * point1x);
                    }
                } else if (point4x > point1x) {
                    mZ = (point41y - point4y) / (cellColumnCount - Math.abs(point4x - point41x));
                    bZ = point4y - (mZ * point4x);
                    bZ1 = point41y - (mZ * point41x);
                }

                if (point41x < point1x) {
                    m1 = (point41y - point1y) / (point41x - point1x);
                    b1 = point1y - (m1 * point1x);
                } else if (point41x > point1x) {
                    m1 = (point1y - point41y) / (cellColumnCount - Math.abs(point41x - point1x));
                    b1 = point41y - (m1 * point41x);
                }

                if (point12x > point1x) {
                    m2 = (point1y - point12y) / (point1x - point12x);
                    b2 = point12y - (m2 * point12x);
                } else if (point12x < point1x) {
                    m2 = (point12y - point1y) / (cellColumnCount - Math.abs(point1x - point12x));
                    b2 = point12y - (m2 * point12x);
                }

                if (point2x > point1x || point2x > point12x) {
                    m3 = (point12y - point2y) / (point12x - point2x);
                    b3 = point2y - (m3 * point2x);
                    if (point2x > point12x && point2x < point1x) {
                        m30 = (point12y - point1y) / (cellColumnCount - Math.abs(point12x - point1x));
                        b30 = (point1y + 2) - (m30 * point1x);
                    }
                } else if (point2x < point1x) {
                    m3Z = (point2y - point12y) / (cellColumnCount - Math.abs(point2x - point12x));
                    b3Z = point2y - (m3Z * point2x);
                    b3Z1 = point12y - (m3Z * point12x);
                }

                //Lower Half
                if (point4x < point3x || point4x < point34x) {
                    m4 = (point4y - point34y) / (point4x - point34x);
                    b4 = point34y - (m4 * point34x);
                    if (point4x < point34x && point4x > point3x) {
                        m40 = (point3y - point34y) / (cellColumnCount - Math.abs(point3x - point34x));
                        b40 = (point3y - 2) - (m40 * point3x);
                    }
                } else if (point4x > point3x) {
                    m4Z = (point34y - point4y) / (cellColumnCount - Math.abs(point4x - point34x));
                    b4Z = point4y - (m4Z * point4x);
                    b4Z1 = point34y - (m4Z * point34x);
                }

                if (point34x < point3x) {
                    m5 = (point34y - point3y) / (point34x - point3x);
                    b5 = point3y - (m5 * point3x);
                } else if (point34x > point3x) {
                    m5 = (point3y - point34y) / (cellColumnCount - Math.abs(point34x - point3x));
                    b5 = point34y - (m5 * point34x);
                }

                if (point23x > point3x) {
                    m6 = (point3y - point23y) / (point3x - point23x);
                    b6 = point23y - (m6 * point23x);
                } else if (point23x < point3x) {
                    m6 = (point23y - point3y) / (cellColumnCount - Math.abs(point23x - point3x));
                    b6 = point23y - (m6 * point23x);
                }

                if (point2x > point3x || point2x > point23x) {
                    m7 = (point23y - point2y) / (point23x - point2x);
                    b7 = point2y - (m7 * point2x);
                    if (point2x > point23x && point2x < point3x) {
                        m70 = (point23y - point3y) / (cellColumnCount - Math.abs(point23x - point3x));
                        b70 = (point3y - 2) - (m70 * point3x);
                    }
                } else if (point2x < point3x) {
                    m7Z = (point2y - point23y) / (cellColumnCount - Math.abs(point2x - point23x));
                    b7Z = point2y - (m7Z * point2x);
                    b7Z1 = point23y - (m7Z * point23x);
                }

                for (int c = 0; c < cellColumnCount; c++) {
                    for (int r = 0; r < cellRowCount; r++) {
                        //Upper Half
                        if (point4x < point1x || point4x < point41x) {
                            if (c > (point4x + trimEdge) && c <= point41x && r <= point4y && r > (m * c) + b) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            } else if (point4x < point41x && point4x > point1x && c <= point1x && c >= 0 && r <= point4y && r > (m0 * c) + b0) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        } else if (point4x > point1x) {
                            if (c > (point4x + trimEdge) && c < cellColumnCount && r <= point4y && r > (mZ * c) + bZ) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            } else if (c <= point41x && c >= 0 && r <= point4y && r > (mZ * c) + bZ1) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        }

                        if (point41x < point1x) {    //MOD
                            if (c >= point41x && c <= point1x && r <= Math.max(point4y, point2y) && (point4x > point3x ? r < (m4Z * c) + b4Z1 : r < (m4 * c) + b4) && r < (m5 * c) + b5 && r > (m1 * c) + b1 && r > (point1y + trimEdge)) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        } else if (point41x > point1x) {
                            if (c >= point41x && c < cellColumnCount && r <= point4y && r > (m1 * c) + b1 && r > (point1y + trimEdge)) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        }

                        if (point12x > point1x) {   //MOD
                            if (c >= point1x && c <= point12x && r <= Math.max(point2y, point4y) && r < (m6 * c) + b6 && (point2x < point3x ? r < (m7Z * c) + b7Z1 : r < (m7 * c) + b7) && r > (m2 * c) + b2 && r > (point1y + trimEdge)) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        } else if (point12x < point1x) {
                            if (c <= point12x && c >= 0 && r <= point2y && r > (m2 * c) + b2 && r > (point1y + trimEdge)) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        }

                        if (point2x > point1x || point2x > point12x) {
                            if (c >= point12x && c < (point2x - trimEdge) && r <= point2y && r > (m3 * c) + b3) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            } else if (point2x > point12x && point2x < point1x && c >= point1x && c < cellColumnCount && r <= point2y && r > (m30 * c) + b30) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        } else if (point2x < point1x) {
                            if (c < (point2x + trimEdge) && c >= 0 && r <= point2y && r > (m3Z * c) + b3Z) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            } else if (c >= point12x && c < cellColumnCount && r <= point2y && r > (m3Z * c) + b3Z1) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        }
                        //Lower Half
                        if (point4x < point3x || point4x < point34x) {
                            if (c > (point4x + trimEdge) && c <= point34x && r >= point4y && r < (m4 * c) + b4) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            } else if (point4x < point34x && point4x > point3x && c <= point3x && c >= 0 && r >= point4y && r < (m40 * c) + b40) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        } else if (point4x > point3x) {
                            if (c > (point4x + trimEdge) && c < cellColumnCount && r >= point4y && r < (m4Z * c) + b4Z) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            } else if (c <= point34x && c >= 0 && r >= point4y && r < (m4Z * c) + b4Z1) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        }

                        if (point34x < point3x) {  //MOD
                            if (c >= point34x && c <= point3x && r >= Math.min(point4y, point2y) && (point4x > point1x ? r > (mZ * c) + bZ1 : r > (m * c) + b) && r > (m1 * c) + b1 && r < (m5 * c) + b5 && r < (point3y - trimEdge)) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        } else if (point34x > point3x) {
                            if (c >= point34x && c < cellColumnCount && r >= point4y && r < (m5 * c) + b5 && r > (point1y + trimEdge)) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        }


                        if (point23x > point3x) {    //MOD
                            if (c >= point3x && c <= point23x && r >= Math.min(point2y, point4y) && (point2x < point1x ? r > (m3Z * c) + b3Z1 : r > (m3 * c) + b3) && r > (m2 * c) + b2 && r < (m6 * c) + b6 && r < (point3y - trimEdge)) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        } else if (point23x < point3x) {
                            if (c <= point23x && c >= 0 && r >= point2y && r < (m6 * c) + b6 && r > (point1y + trimEdge)) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        }

                        if (point2x > point3x || point2x > point23x) {
                            if (c >= point23x && c < (point2x - trimEdge) && r >= point2y && r < (m7 * c) + b7) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            } else if (point2x > point23x && point2x < point3x && c >= point3x && c < cellColumnCount && r >= point2y && r < (m70 * c) + b70) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        } else if (point2x < point3x) {
                            if (c < (point2x + trimEdge) && c >= 0 && r >= point2y && r < (m7Z * c) + b7Z) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            } else if (c >= point23x && c < cellColumnCount && r >= point2y && r < (m7Z * c) + b7Z1) {
                                grid[c][r].changeCellColor(freshBlue);
                                grid[c][r].isLake = true;
                            }
                        }

                    }
                }
                lakePlaced = true;
                repaint();
            }
        }
    }

    public void lakePlacer(){
        int lakeCount = 0;
        while (lakeCount < 3) {
            lakeMaker(1, 1);
            lakeCount++;
        }
        repaint();
    }

    public void addNoise() {
        for(int c=0;c<cellColumnCount;c++) {
            for (int r = 0; r < cellRowCount; r++) {
                int greenNum = 0;
                int freshBlueNum = 0;
                int neighborCount = grid[c][r].getNeighborCount();
                GridCell[] tempNeighbors = grid[c][r].getNeighbors();
                for (int n = 0; n < neighborCount; n++) {
                    if (tempNeighbors[n].color.equals(landGreen)) {
                        greenNum++;
                    }
                    else if (tempNeighbors[n].color.equals(freshBlue)) {
                        freshBlueNum++;
                    }
                }

                if (greenNum > 3 && !grid[c][r].color.equals(freshBlue)) {
                    grid[c][r].nextColor = landGreen;
                }
                else if (greenNum > 0 && greenNum < 4 && !grid[c][r].color.equals(freshBlue)) {
                    double chance = Math.random();
                    if (chance < 0.85) {
                        grid[c][r].nextColor = landGreen;
                    }
                }
                if (freshBlueNum > 2) {
                    grid[c][r].nextColor = freshBlue;
                }
                /*else if (freshBlueNum > 0 && freshBlueNum < 3) {
                    double chance = Math.random();
                    if (chance < 0.95) {
                        grid[c][r].nextColor = freshBlue;
                    }
                }*/
            }
        }
        for(int c=0;c<cellColumnCount;c++) {
            for (int r = 0; r < cellRowCount; r++) {
                if (!grid[c][r].nextColor.equals(Color.black)) {
                    grid[c][r].color = grid[c][r].nextColor;
                }
            }
        }
        noOceanPuddles();
        repaint();
    }

    public void noOceanPuddles() {
        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                int neighborCount = grid[c][r].getNeighborCount();
                GridCell[] tempNeighbors = grid[c][r].getNeighbors();
                int greenNum = 0;

                for (int n = 0; n < neighborCount; n++) {
                    if (tempNeighbors[n].color.equals(landGreen)) {
                        greenNum++;
                    }
                }

                if (greenNum == 4) {
                    grid[c][r].color = landGreen;
                }
            }
        }
    }

    public void defineCoasts() {
        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                int neighborCount = grid[c][r].getNeighborCount();
                GridCell[] tempNeighbors = grid[c][r].getNeighbors();
                int oceanCount = 0;
                for (int n = 0; n < neighborCount; n++) {
                    if (tempNeighbors[n].color.equals(oceanBlue) && grid[c][r].color.equals(landGreen)) {
                        oceanCount++;
                    }
                }
                if (oceanCount >= 1) {grid[c][r].isCoast = true;}
            }
        }
    }

    public void addMountains() {
        //for (int mountain = 0; mountain < 20; mountain++) {
        boolean mountainsPlaced = false;
        while(mountainsPlaced == false) {
            int mtnX = (int)(Math.floor(Math.random() * (cellColumnCount)));
            int mtnY = (int)(Math.floor(Math.random() * (cellRowCount-10)+5));
            if (grid[mtnX][mtnY].color.equals(landGreen)) {
                int directionX = (Math.floor(Math.random()*2)) == 1 ? 1 : -1;
                int directionY = (Math.floor(Math.random()*2)) == 1 ? 1 : -1;
                int rangeSize = (int)(Math.floor(Math.random() * 25) + 20);
                for (int i = 0; i < rangeSize; i++) {
                    if (!grid[mtnX][mtnY].color.equals(freshBlue) && !grid[mtnX][mtnY].color.equals(oceanBlue)) {
                        grid[mtnX][mtnY].color = mountainWhite;
                    }
                    double flowDirection = Math.random();
                    if (flowDirection < 0.5) {
                        mtnY += directionY;
                        if (mtnY < 0) {mtnY = 0;}
                        if (mtnY >= cellRowCount) {mtnY = (cellRowCount-1);}
                    }
                    else if (flowDirection < 1) {
                        mtnX += directionX;
                        if (mtnX < 0) {mtnX = (cellColumnCount-1);}
                        if (mtnX >= cellColumnCount) {mtnX = 0;}
                    }
                }
                mountainsPlaced = true;
            }
        }
    }

    public void mountainPlacer() {
        for (int m = 0; m < 7; m++){
            addMountains();
        }
        double mtnPercent = 0;
        while (mtnPercent < 0.04) {
            int land = 0;
            int mtn = 0;
            for (int c = 0; c < cellColumnCount; c++) {
                for (int r = 0; r < cellRowCount; r++) {
                    if (grid[c][r].color == landGreen) {
                        land++;
                    }
                    if (grid[c][r].color == mountainWhite) {
                        mtn++;
                    }
                }
            }
            mtnPercent = ((double) mtn) / (((double) land) + ((double) mtn));
            //System.out.println("PercentLand: " + landPercent + " Land: " + land + " Water: " + water);
            if (mtnPercent < 0.04) {
                addMountains();
            }
        }
        enhanceMountains();
        noMountainIslands();
        repaint();
    }

    public void enhanceMountains() {
        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                int whiteCount = 0;
                int neighborCount = grid[c][r].getNeighborCount();
                GridCell[] tempNeighbors = grid[c][r].getNeighbors();

                for (int n = 0; n < neighborCount; n++) {
                    if (tempNeighbors[n].color.equals(mountainWhite)) {
                        whiteCount++;
                    }
                }

                if (whiteCount > 0 && !grid[c][r].color.equals(freshBlue) && !grid[c][r].color.equals(oceanBlue)) {
                    double chance = Math.random();
                    if (chance < 0.80) {
                        grid[c][r].nextColor = mountainWhite;
                    }
                }
            }
        }
        for(int c=0;c<cellColumnCount;c++) {
            for (int r = 0; r < cellRowCount; r++) {
                if (grid[c][r].nextColor.equals(mountainWhite)) {
                    grid[c][r].color = grid[c][r].nextColor;
                }
            }
        }
    }

    public void noMountainIslands() { //Makes sure there's no single cell pockets of non mountain terrain within mountain ranges
        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                int neighborCount = grid[c][r].getNeighborCount();
                GridCell[] tempNeighbors = grid[c][r].getNeighbors();
                int whiteNum = 0;

                for (int n = 0; n < neighborCount; n++) {
                    if (tempNeighbors[n].color.equals(mountainWhite)) {
                        whiteNum++;
                    }
                }

                if (whiteNum == 4) {
                    grid[c][r].color = mountainWhite;
                }
            }
        }
    }

    public void addRivers() {
        try {
            double Ndist = 0, NEdist = 0, Edist = 0, SEdist = 0, Sdist = 0, SWdist = 0, Wdist = 0, NWdist = 0;
            for(int river = 0; river < 20; river++) { //adds 20 rivers - configurable
                //boolean riverPlaced = false;
                //while(riverPlaced == false) {
                int lakeX = (int) (Math.floor(Math.random() * (cellColumnCount)));
                int lakeY = (int) (Math.floor(Math.random() * (cellRowCount - 10) + 5));
                if (lakeY >= cellRowCount) {
                    lakeY = (cellRowCount - 1);
                }
                int cloneX = lakeX;
                int cloneY = lakeY;
                boolean tooCloseN = false, tooCloseNE = false, tooCloseE = false, tooCloseSE = false, tooCloseS = false, tooCloseSW = false, tooCloseW = false, tooCloseNW = false;
                Ndist = 0;
                NEdist = 0;
                Edist = 0;
                SEdist = 0;
                Sdist = 0;
                SWdist = 0;
                Wdist = 0;
                NWdist = 0;
                for (int i = 0; i < 4; i++) {
                    if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                        tooCloseN = true;
                    }
                    Ndist--;
                    if (grid[lakeX][(cellRowCount + lakeY + (int) Ndist) % cellRowCount].color.equals(oceanBlue)) {
                        tooCloseN = true;
                    }
                }
                for (int i = 0; i < 4; i++) {
                    if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                        tooCloseN = true;
                    }
                    Wdist--;
                    if (grid[(cellColumnCount + (lakeX + (int) Wdist)) % cellColumnCount][lakeY].color.equals(oceanBlue)) {
                        tooCloseW = true;
                    }
                }
                for (int i = 0; i < 4; i++) {
                    if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                        tooCloseN = true;
                    }
                    Sdist++;
                    if (grid[lakeX][(lakeY + (int) Sdist) % cellRowCount].color.equals(oceanBlue)) {
                        tooCloseS = true;
                    }
                }
                for (int i = 0; i < 4; i++) {
                    if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                        tooCloseN = true;
                    }
                    Edist++;
                    if (grid[(lakeX + (int) Edist) % cellColumnCount][lakeY].color.equals(oceanBlue)) {
                        tooCloseE = true;
                    }
                }
                for (int i = 0; i < 2; i++) {
                    if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                        tooCloseN = true;
                    }
                    NWdist++;
                    if (grid[(cellColumnCount + lakeX - (int) NWdist) % cellColumnCount][(cellRowCount + lakeY - (int) NWdist) % cellRowCount].color.equals(oceanBlue)) {
                        tooCloseNW = true;
                    }
                }
                for (int i = 0; i < 2; i++) {
                    if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                        tooCloseN = true;
                    }
                    SWdist++;
                    if (grid[(cellColumnCount + lakeX - (int) SWdist) % cellColumnCount][(lakeY + (int) SWdist) % cellRowCount].color.equals(oceanBlue)) {
                        tooCloseSW = true;
                    }
                }
                for (int i = 0; i < 2; i++) {
                    if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                        tooCloseN = true;
                    }
                    SEdist++;
                    if (grid[(lakeX + (int) SEdist) % cellColumnCount][(lakeY + (int) SEdist) % cellRowCount].color.equals(oceanBlue)) {
                        tooCloseSE = true;
                    }
                }
                for (int i = 0; i < 2; i++) {
                    if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                        tooCloseN = true;
                    }
                    NEdist++;
                    if (grid[(lakeX + (int) NEdist) % cellColumnCount][(cellRowCount + lakeY - (int) NEdist) % cellRowCount].color.equals(oceanBlue)) {
                        tooCloseNE = true;
                    }
                }
                if (tooCloseN == false && tooCloseS == false && tooCloseE == false && tooCloseW == false && tooCloseNE == false && tooCloseNW == false && tooCloseSE == false && tooCloseSW == false) {
                    boolean Ncheck = false, NEcheck = false, Echeck = false, SEcheck = false, Scheck = false, SWcheck = false, Wcheck = false, NWcheck = false;
                    Ndist = 0;
                    NEdist = 0;
                    Edist = 0;
                    SEdist = 0;
                    Sdist = 0;
                    SWdist = 0;
                    Wdist = 0;
                    NWdist = 0;
                    for(int i = 0; i < (cellRowCount); i++) {
                        if (grid[lakeX][lakeY].color.equals(landGreen) || grid[lakeX][lakeY].color.equals(freshBlue)) {
                            Ndist++;
                            lakeY--;
                        } else if (grid[lakeX][lakeY].color.equals(oceanBlue)) {
                            Ncheck = true;
                            break;
                        } else if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                            Ndist = 500;
                            Ncheck = true;
                            break;
                        }
                    }
                    lakeX = cloneX;
                    lakeY = cloneY;
                    for(int i = 0; i < (cellRowCount); i++) {
                        if (grid[lakeX][lakeY].color.equals(landGreen) || grid[lakeX][lakeY].color.equals(freshBlue)) {
                            NEdist += 1.4;
                            lakeY--;
                            lakeX++;
                            if (lakeX < 0) {
                                lakeX = (cellColumnCount - 1);
                            } else if (lakeX >= cellColumnCount) {
                                lakeX = 0;
                            }
                        } else if (grid[lakeX][lakeY].color.equals(oceanBlue)) {
                            NEcheck = true;
                            break;
                        } else if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                            NEdist = 500;
                            NEcheck = true;
                            break;
                        }
                    }
                    lakeX = cloneX;
                    lakeY = cloneY;
                    //int eastCounter = 0;
                    //while (Echeck == false /*|| eastCounter < 126) {
                    for(int i = 0; i < (cellColumnCount+1); i++) {
                        if (grid[lakeX][lakeY].color.equals(landGreen) || grid[lakeX][lakeY].color.equals(freshBlue)) {
                            Edist++;
                            lakeX++;
                            if (lakeX < 0) {
                                lakeX = (cellColumnCount - 1);
                            } else if (lakeX >= cellColumnCount) {
                                lakeX = 0;
                            }
                            //eastCounter++;
                        }
                        else if (grid[lakeX][lakeY].color.equals(oceanBlue)) {
                            Echeck = true;
                            break;
                        } else if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                            Edist = 500;
                            Echeck = true;
                            break;
                        }
                    }
                    lakeX = cloneX;
                    lakeY = cloneY;
                    for(int i = 0; i < (cellRowCount); i++) {
                        if (grid[lakeX][lakeY].color.equals(landGreen) || grid[lakeX][lakeY].color.equals(freshBlue)) {
                            SEdist += 1.4;
                            lakeX++;
                            lakeY++;
                            if (lakeX < 0) {
                                lakeX = (cellColumnCount - 1);
                            } else if (lakeX >= cellColumnCount) {
                                lakeX = 0;
                            }
                        } else if (grid[lakeX][lakeY].color.equals(oceanBlue)) {
                            SEcheck = true;
                            break;
                        } else if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                            SEdist = 500;
                            SEcheck = true;
                            break;
                        }
                    }
                    lakeX = cloneX;
                    lakeY = cloneY;
                    for(int i = 0; i < (cellRowCount); i++) {
                        if (grid[lakeX][lakeY].color.equals(landGreen) || grid[lakeX][lakeY].color.equals(freshBlue)) {
                            Sdist++;
                            lakeY++;
                            if (lakeY >= cellRowCount) {
                                lakeY = (cellRowCount - 1);
                            }
                        } else if (grid[lakeX][lakeY].color.equals(oceanBlue)) {
                            Scheck = true;
                            break;
                        } else if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                            Sdist = 500;
                            Scheck = true;
                            break;
                        }
                    }
                    lakeX = cloneX;
                    lakeY = cloneY;
                    for(int i = 0; i < (cellRowCount); i++) {
                        if (grid[lakeX][lakeY].color.equals(landGreen) || grid[lakeX][lakeY].color.equals(freshBlue)) {
                            SWdist += 1.4;
                            lakeY++;
                            lakeX--;
                            if (lakeY >= cellRowCount) {
                                lakeY = (cellRowCount - 1);
                            }
                            if (lakeX < 0) {
                                lakeX = (cellColumnCount - 1);
                            } else if (lakeX >= cellColumnCount) {
                                lakeX = 0;
                            }
                        } else if (grid[lakeX][lakeY].color.equals(oceanBlue)) {
                            SWcheck = true;
                            break;
                        } else if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                            SWdist = 500;
                            SWcheck = true;
                            break;
                        }
                    }
                    lakeX = cloneX;
                    lakeY = cloneY;
                    //int westCounter = 0;
                    //while (Wcheck == false/* || westCounter < 126) {
                    for(int i = 0; i < (cellColumnCount+1); i++) {
                        if (grid[lakeX][lakeY].color.equals(landGreen) || grid[lakeX][lakeY].color.equals(freshBlue)) {
                            Wdist++;
                            lakeX--;
                            if (lakeX < 0) {
                                lakeX = (cellColumnCount - 1);
                            } else if (lakeX >= cellColumnCount) {
                                lakeX = 0;
                            }
                            //westCounter++;
                        }
                        else if (grid[lakeX][lakeY].color.equals(oceanBlue)) {
                            Wcheck = true;
                            break;
                        }
                        else if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                            Wdist = 500;
                            Wcheck = true;
                            break;
                        }
                    }
                    lakeX = cloneX;
                    lakeY = cloneY;
                    for(int i = 0; i < (cellRowCount); i++) {
                        if (grid[lakeX][lakeY].color.equals(landGreen) || grid[lakeX][lakeY].color.equals(freshBlue)) {
                            NWdist += 1.4;
                            lakeX--;
                            lakeY--;
                            if (lakeX < 0) {
                                lakeX = (cellColumnCount - 1);
                            } else if (lakeX >= cellColumnCount) {
                                lakeX = 0;
                            }
                        } else if (grid[lakeX][lakeY].color.equals(oceanBlue)) {
                            NWcheck = true;
                            break;
                        } else if (grid[lakeX][lakeY].color.equals(mountainWhite)) {
                            NWdist = 500;
                            NWcheck = true;
                            break;
                        }
                    }
                    lakeX = cloneX;
                    lakeY = cloneY;

                    int NdistInt = 0, NEdistInt = 0, EdistInt = 0, SEdistInt = 0, SdistInt = 0, SWdistInt = 0, WdistInt = 0, NWdistInt = 0;
                    NdistInt = (int) Ndist;
                    NEdistInt = (int) NEdist;
                    EdistInt = (int) Edist;
                    SEdistInt = (int) SEdist;
                    SdistInt = (int) Sdist;
                    SWdistInt = (int) SWdist;
                    WdistInt = (int) Wdist;
                    NWdistInt = (int) NWdist;
                    int closestDist = (Math.min(NdistInt, Math.min(NEdistInt, Math.min(EdistInt, Math.min(SEdistInt, Math.min(SdistInt, Math.min(SWdistInt, Math.min(WdistInt, NWdistInt))))))));
                    //console.log(Ndist + " " + NEdist + " " + Edist + " " + SEdist + " " + Sdist + " " + SWdist + " " + Wdist + " " + NWdist);
                    //console.log(closestDist);
                    int xMove = 0, yMove = 0;
                    int yCount = 0, xCount = 0;
                    int posDirection = 0, negDirection = 0;
                    int blueCount = 0;

                    if (closestDist == NdistInt) {
                        yMove = -1;
                        xMove = 1;
                        while (blueCount < 1 && !grid[lakeX][lakeY].color.equals(freshBlue)) {
                            if (grid[lakeX][lakeY].isCoast == true) {
                                blueCount++;
                            }
                            int riverNeighbor = 0;
                            if (lakeX == 0) {
                                if (grid[cellColumnCount - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else if (lakeX == (cellColumnCount - 1)) {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[0][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            }
                            if (riverNeighbor >= 2) {
                                blueCount = 1;
                            }
                            grid[lakeX][lakeY].color = freshBlue;
                            grid[lakeX][lakeY].isRiver = true;
                            double flowDirection = Math.random();
                            if (flowDirection < 0.66) {
                                lakeY += yMove;
                                yCount++;
                            } else if ((flowDirection < 0.83 && yCount >= 2) || (flowDirection < 0.83 && negDirection == 0)) {
                                lakeX += xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                posDirection++;
                                yCount = 0;
                                negDirection = 0;
                            } else if ((flowDirection < 1 && yCount >= 2) || (flowDirection < 1 && posDirection == 0)) {
                                lakeX -= xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                negDirection++;
                                yCount = 0;
                                posDirection = 0;
                            } else {
                                lakeY += yMove;
                                yCount++;
                            }
                        }
                    } else if (closestDist == NEdistInt) {
                        yMove = -1;
                        xMove = 1;
                        while (blueCount < 1 && !grid[lakeX][lakeY].color.equals(freshBlue)) {
                            if (grid[lakeX][lakeY].isCoast == true) {
                                blueCount++;
                            }
                            int riverNeighbor = 0;
                            if (lakeX == 0) {
                                if (grid[cellColumnCount - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else if (lakeX == (cellColumnCount - 1)) {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[0][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            }
                            if (riverNeighbor >= 2) {
                                blueCount = 1;
                            }
                            grid[lakeX][lakeY].color = freshBlue;
                            grid[lakeX][lakeY].isRiver = true;
                            double flowDirection = Math.random();
                            if (flowDirection < 0.5) {
                                lakeY += yMove;
                                yCount++;
                            } else if ((flowDirection < 0.83 && yCount >= 2) || (flowDirection < 0.83 && negDirection == 0)) {
                                lakeX += xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                posDirection++;
                                yCount = 0;
                                negDirection = 0;
                            } else if ((flowDirection < 1 && yCount >= 2) || (flowDirection < 1 && posDirection == 0)) {
                                lakeX -= xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                negDirection++;
                                yCount = 0;
                                posDirection = 0;
                            } else {
                                lakeY += yMove;
                                yCount++;
                            }
                        }
                    } else if (closestDist == EdistInt) {
                        yMove = 1;
                        xMove = 1;
                        while (blueCount < 1 && !grid[lakeX][lakeY].color.equals(freshBlue)) {
                            if (grid[lakeX][lakeY].isCoast == true) {
                                blueCount++;
                            }
                            int riverNeighbor = 0;
                            if (lakeX == 0) {
                                if (grid[cellColumnCount - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else if (lakeX == (cellColumnCount - 1)) {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[0][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            }
                            if (riverNeighbor >= 2) {
                                blueCount = 1;
                            }
                            grid[lakeX][lakeY].color = freshBlue;
                            grid[lakeX][lakeY].isRiver = true;
                            double flowDirection = Math.random();
                            if (flowDirection < 0.66) {
                                lakeX += xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                xCount++;

                            } else if ((flowDirection < 0.83 && xCount >= 2) || (flowDirection < 0.83 && negDirection == 0)) {
                                lakeY += yMove;
                                posDirection++;
                                xCount = 0;
                                negDirection = 0;
                            } else if ((flowDirection < 1 && xCount >= 2) || (flowDirection < 1 && posDirection == 0)) {
                                lakeY -= yMove;
                                negDirection++;
                                xCount = 0;
                                posDirection = 0;
                            } else {
                                lakeY += yMove;
                                yCount++;
                            }
                        }
                    } else if (closestDist == SEdistInt) {
                        yMove = 1;
                        xMove = 1;
                        while (blueCount < 1 && !grid[lakeX][lakeY].color.equals(freshBlue)) {
                            if (grid[lakeX][lakeY].isCoast == true) {
                                blueCount++;
                            }
                            int riverNeighbor = 0;
                            if (lakeX == 0) {
                                if (grid[cellColumnCount - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else if (lakeX == (cellColumnCount - 1)) {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[0][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            }
                            if (riverNeighbor >= 2) {
                                blueCount = 1;
                            }
                            grid[lakeX][lakeY].color = freshBlue;
                            grid[lakeX][lakeY].isRiver = true;
                            double flowDirection = Math.random();
                            if (flowDirection < 0.5) {
                                lakeY += yMove;
                                yCount++;
                            } else if ((flowDirection < 0.83 && yCount >= 2) || (flowDirection < 0.83 && negDirection == 0)) {
                                lakeX += xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                posDirection++;
                                yCount = 0;
                                negDirection = 0;
                            } else if ((flowDirection < 1 && yCount >= 2) || (flowDirection < 1 && posDirection == 0)) {
                                lakeX -= xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                negDirection++;
                                yCount = 0;
                                posDirection = 0;
                            } else {
                                lakeY += yMove;
                                yCount++;
                            }
                        }
                    } else if (closestDist == SdistInt) {
                        yMove = 1;
                        xMove = 1;
                        while (blueCount < 1 && !grid[lakeX][lakeY].color.equals(freshBlue)) {
                            if (grid[lakeX][lakeY].isCoast == true) {
                                blueCount++;
                            }
                            int riverNeighbor = 0;
                            if (lakeX == 0) {
                                if (grid[cellColumnCount - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else if (lakeX == (cellColumnCount - 1)) {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[0][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            }
                            if (riverNeighbor >= 2) {
                                blueCount = 1;
                            }
                            grid[lakeX][lakeY].color = freshBlue;
                            grid[lakeX][lakeY].isRiver = true;
                            double flowDirection = Math.random();
                            if (flowDirection < 0.66) {
                                lakeY += yMove;
                                yCount++;
                            } else if ((flowDirection < 0.83 && yCount >= 2) || (flowDirection < 0.83 && negDirection == 0)) {
                                lakeX += xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                posDirection++;
                                yCount = 0;
                                negDirection = 0;
                            } else if ((flowDirection < 1 && yCount >= 2) || (flowDirection < 1 && posDirection == 0)) {
                                lakeX -= xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                negDirection++;
                                yCount = 0;
                                posDirection = 0;
                            } else {
                                lakeY += yMove;
                                yCount++;
                            }
                        }
                    } else if (closestDist == SWdistInt) {
                        yMove = 1;
                        xMove = -1;
                        while (blueCount < 1 && !grid[lakeX][lakeY].color.equals(freshBlue)) {
                            if (grid[lakeX][lakeY].isCoast == true) {
                                blueCount++;
                            }
                            int riverNeighbor = 0;
                            if (lakeX == 0) {
                                if (grid[cellColumnCount - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else if (lakeX == (cellColumnCount - 1)) {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[0][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            }
                            if (riverNeighbor >= 2) {
                                blueCount = 1;
                            }
                            grid[lakeX][lakeY].color = freshBlue;
                            grid[lakeX][lakeY].isRiver = true;
                            double flowDirection = Math.random();
                            if (flowDirection < 0.5) {
                                lakeY += yMove;
                                yCount++;
                            } else if ((flowDirection < 0.83 && yCount >= 2) || (flowDirection < 0.83 && negDirection == 0)) {
                                lakeX += xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                posDirection++;
                                yCount = 0;
                                negDirection = 0;
                            } else if ((flowDirection < 1 && yCount >= 2) || (flowDirection < 1 && posDirection == 0)) {
                                lakeX -= xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                negDirection++;
                                yCount = 0;
                                posDirection = 0;
                            } else {
                                lakeY += yMove;
                                yCount++;
                            }
                        }
                    } else if (closestDist == WdistInt) {
                        yMove = 1;
                        xMove = -1;
                        while (blueCount < 1 && !grid[lakeX][lakeY].color.equals(freshBlue)) {
                            if (grid[lakeX][lakeY].isCoast == true) {
                                blueCount++;
                            }
                            int riverNeighbor = 0;
                            if (lakeX == 0) {
                                if (grid[cellColumnCount - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else if (lakeX == (cellColumnCount - 1)) {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[0][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            }
                            if (riverNeighbor >= 2) {
                                blueCount = 1;
                            }
                            grid[lakeX][lakeY].color = freshBlue;
                            grid[lakeX][lakeY].isRiver = true;
                            double flowDirection = Math.random();
                            if (flowDirection < 0.66) {
                                lakeX += xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                xCount++;

                            } else if ((flowDirection < 0.83 && xCount >= 2) || (flowDirection < 0.83 && negDirection == 0)) {
                                lakeY += yMove;
                                posDirection++;
                                xCount = 0;
                                negDirection = 0;
                            } else if ((flowDirection < 1 && xCount >= 2) || (flowDirection < 1 && posDirection == 0)) {
                                lakeY -= yMove;
                                negDirection++;
                                xCount = 0;
                                posDirection = 0;
                            } else {
                                lakeY += yMove;
                                yCount++;
                            }
                        }
                    } else if (closestDist == NWdistInt) {
                        yMove = -1;
                        xMove = -1;
                        while (blueCount < 1 && !grid[lakeX][lakeY].color.equals(freshBlue)) {
                            if (grid[lakeX][lakeY].isCoast == true) {
                                blueCount++;
                            }
                            int riverNeighbor = 0;
                            if (lakeX == 0) {
                                if (grid[cellColumnCount - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else if (lakeX == (cellColumnCount - 1)) {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[0][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            } else {
                                if (grid[lakeX - 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX + 1][lakeY].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY - 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                                if (grid[lakeX][lakeY + 1].color.equals(freshBlue)) {
                                    riverNeighbor++;
                                }
                            }
                            if (riverNeighbor >= 2) {
                                blueCount = 1;
                            }
                            grid[lakeX][lakeY].color = freshBlue;
                            grid[lakeX][lakeY].isRiver = true;
                            double flowDirection = Math.random();
                            if (flowDirection < 0.5) {
                                lakeY += yMove;
                                yCount++;
                            } else if ((flowDirection < 0.83 && yCount >= 2) || (flowDirection < 0.83 && negDirection == 0)) {
                                lakeX += xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                posDirection++;
                                yCount = 0;
                                negDirection = 0;
                            } else if ((flowDirection < 1 && yCount >= 2) || (flowDirection < 1 && posDirection == 0)) {
                                lakeX -= xMove;
                                if (lakeX < 0) {
                                    lakeX = (cellColumnCount - 1);
                                } else if (lakeX >= cellColumnCount) {
                                    lakeX = 0;
                                }
                                negDirection++;
                                yCount = 0;
                                posDirection = 0;
                            } else {
                                lakeY += yMove;
                                yCount++;
                            }
                        }
                    }
                    //riverPlaced = true;
                    river = 20;
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException error) {
            return;
        }
    }

    public void riverPlacer() {
        for (int m = 0; m < 11; m++){
            addRivers();
        }
        double freshPercent = 0;
        while (freshPercent < 0.07) {
            int land = 0;
            int mtn = 0;
            int freshWater = 0;
            for (int c = 0; c < cellColumnCount; c++) {
                for (int r = 0; r < cellRowCount; r++) {
                    if (grid[c][r].color == landGreen) {
                        land++;
                    }
                    if (grid[c][r].color == mountainWhite) {
                        mtn++;
                    }
                    if(grid[c][r].color == freshBlue) {
                        freshWater++;
                    }
                }
            }
            freshPercent = ((double) freshWater) / (((double) land) + ((double) mtn) + ((double) freshWater));
            //System.out.println("PercentLand: " + landPercent + " Land: " + land + " Water: " + water);
            if (freshPercent < 0.07) {
                addRivers();
            }
        }
        repaint();
    }

    public void defineWaterAvail(String windDirection) {
        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                boolean Ncheck = false, NEcheck = false, Echeck = false, SEcheck = false, Scheck = false, SWcheck = false, Wcheck = false, NWcheck = false;
                double Ndist = 0, NEdist = 0, Edist = 0, SEdist = 0, Sdist = 0, SWdist = 0, Wdist = 0, NWdist = 0;
                double Nmtn = 1, NEmtn = 1, Emtn = 1, SEmtn = 1, Smtn = 1, SWmtn = 1, Wmtn = 1, NWmtn = 1;
                double NdistRiver = 0, NEdistRiver = 0, EdistRiver = 0, SEdistRiver = 0, SdistRiver = 0, SWdistRiver = 0, WdistRiver = 0, NWdistRiver = 0;
                double NdistLake = 0, NEdistLake = 0, EdistLake = 0, SEdistLake = 0, SdistLake = 0, SWdistLake = 0, WdistLake = 0, NWdistLake = 0;
                boolean riverFound = false, lakeFound = false;
                int cloneC = c;
                int cloneR = r;
                while (Ncheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        Ndist++;
                        r--;
                        if (r < 0) {break;}
                        if (grid[c][r].color == mountainWhite) {Nmtn = 0.1;}
                        else if (grid[c][r].isRiver = true && riverFound == false) {NdistRiver = Ndist; riverFound = true;}
                        else if (grid[c][r].isLake = true && lakeFound == false) {NdistLake = Ndist; lakeFound = true;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        Ncheck = true;
                    }
                }
                riverFound = false; lakeFound = false;
                c = cloneC;
                r = cloneR;
                while (Wcheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        Wdist++;
                        c--;
                        if (c < 0) {c = (cellColumnCount-1);}
                        if (grid[c][r].color == mountainWhite) {Wmtn = 0.1;}
                        else if (grid[c][r].isRiver = true && riverFound == false) {WdistRiver = Wdist; riverFound = true;}
                        else if (grid[c][r].isLake = true && lakeFound == false) {WdistLake = Wdist; lakeFound = true;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        Wcheck = true;
                    }
                }
                riverFound = false; lakeFound = false;
                c = cloneC;
                r = cloneR;
                while (Scheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        Sdist++;
                        r++;
                        if (r >= cellRowCount) {break;}
                        if (grid[c][r].color == mountainWhite) {Smtn = 0.1;}
                        else if (grid[c][r].isRiver = true && riverFound == false) {SdistRiver = Sdist; riverFound = true;}
                        else if (grid[c][r].isLake = true && lakeFound == false) {SdistLake = Sdist; lakeFound = true;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        Scheck = true;
                    }
                }
                riverFound = false; lakeFound = false;
                c = cloneC;
                r = cloneR;
                while (Echeck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        Edist++;
                        c++;
                        if (c >= cellColumnCount) {c = 0;}
                        if (grid[c][r].color == mountainWhite) {Emtn = 0.1;}
                        else if (grid[c][r].isRiver = true && riverFound == false) {EdistRiver = Edist; riverFound = true;}
                        else if (grid[c][r].isLake = true && lakeFound == false) {EdistLake = Edist; lakeFound = true;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        Echeck = true;
                    }
                }
                riverFound = false; lakeFound = false;
                c = cloneC;
                r = cloneR;
                while (NEcheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        NEdist += 1.4;
                        r--;
                        c++;
                        if (c >= cellColumnCount) {c = 0;}
                        if (r < 0) {break;}
                        if (grid[c][r].color == mountainWhite) {NEmtn = 0.1;}
                        else if (grid[c][r].isRiver = true && riverFound == false) {NEdistRiver = NEdist; riverFound = true;}
                        else if (grid[c][r].isLake = true && lakeFound == false) {NEdistLake = NEdist; lakeFound = true;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        NEcheck = true;
                    }
                }
                riverFound = false; lakeFound = false;
                c = cloneC;
                r = cloneR;
                while (NWcheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        NWdist += 1.4;
                        r--;
                        c--;
                        if (c < 0) {c = (cellColumnCount-1);}
                        if (r < 0) {break;}
                        if (grid[c][r].color == mountainWhite) {NWmtn = 0.1;}
                        else if (grid[c][r].isRiver = true && riverFound == false) {NWdistRiver = NWdist; riverFound = true;}
                        else if (grid[c][r].isLake = true && lakeFound == false) {NWdistLake = NWdist; lakeFound = true;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        NWcheck = true;
                    }
                }
                riverFound = false; lakeFound = false;
                c = cloneC;
                r = cloneR;
                while (SEcheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        SEdist += 1.4;
                        r++;
                        c++;
                        if (c >= cellColumnCount) {c = 0;}
                        if (r >= cellRowCount) {break;}
                        if (grid[c][r].color == mountainWhite) {SEmtn = 0.1;}
                        else if (grid[c][r].isRiver = true && riverFound == false) {SEdistRiver = SEdist; riverFound = true;}
                        else if (grid[c][r].isLake = true && lakeFound == false) {SEdistLake = SEdist; lakeFound = true;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        SEcheck = true;
                    }
                }
                riverFound = false; lakeFound = false;
                c = cloneC;
                r = cloneR;
                while (SWcheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        SWdist += 1.4;
                        r++;
                        c--;
                        if (c < 0) {c = (cellColumnCount-1);}
                        if (r >= cellRowCount) {break;}
                        if (grid[c][r].color == mountainWhite) {SWmtn = 0.1;}
                        else if (grid[c][r].isRiver = true && riverFound == false) {SWdistRiver = SWdist; riverFound = true;}
                        else if (grid[c][r].isLake = true && lakeFound == false) {SWdistLake = SWdist; lakeFound = true;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        SWcheck = true;
                    }
                }
                riverFound = false; lakeFound = false;
                c = cloneC;
                r = cloneR;
                //console.log(c + " " + r);
                //console.log(grid[c][r].water);
                //RIVER AND LAKE ADJACENCY
                if (NdistRiver > 1.9 && NdistRiver < 2.1 || NdistLake > 1.9 && Ndist < 2.1) {grid[c][r].water = 0.9;}
                else if (NEdistRiver > 2.7 && NEdistRiver < 2.9|| NEdistLake > 2.7 && NEdistLake < 2.9) {grid[c][r].water = 0.9;}
                else if (NWdistRiver > 2.7 && NWdistRiver < 2.9|| NWdistLake > 2.7 && NWdistLake < 2.9) {grid[c][r].water = 0.9;}
                else if (WdistRiver > 1.9 && WdistRiver < 2.1|| WdistLake > 1.9 && WdistLake < 2.1) {grid[c][r].water = 0.9;}
                else if (EdistRiver > 1.9 && EdistRiver < 2.1|| EdistLake > 1.9 && EdistLake < 2.1) {grid[c][r].water = 0.9;}
                else if (SEdistRiver > 2.7 && SEdistRiver < 2.9|| SEdistLake > 2.7 && SEdistLake < 2.1) {grid[c][r].water = 0.9;}
                else if (SWdistRiver > 2.7 && SWdistRiver < 2.9|| SWdistLake > 2.7 && SWdistLake < 2.9) {grid[c][r].water = 0.9;}
                else if (SdistRiver > 1.9 && SdistRiver < 2.1 || SdistLake > 1.9 && SdistLake < 2.1) {grid[c][r].water = 0.9;}
                //console.log(grid[c][r].water);

                double Nwater = 0, NEwater = 0, Ewater = 0, SEwater = 0, Swater = 0, SWwater = 0, Wwater = 0, NWwater = 0;

                double distModifier = .75;
                double windBonus = .935;
                double oppositePenalty = .1;
                double semiOppositePenalty = .2;
                double perpendiculars = .45;
                double slantedWind = .835;

                if (windDirection.equals("north")) {
                    Nwater = (Math.pow(distModifier, Ndist) + ((1 - Math.pow(distModifier, Ndist)) * Math.pow(windBonus, Ndist))) * Nmtn;
                    NEwater = Math.pow(distModifier, NEdist) * slantedWind * NEmtn;
                    Ewater = Math.pow(distModifier, Edist) * perpendiculars * Emtn;
                    SEwater = Math.pow(distModifier, SEdist) * semiOppositePenalty * SEmtn;
                    Swater = Math.pow(distModifier, Sdist) * oppositePenalty * Smtn;
                    SWwater = Math.pow(distModifier, SWdist) * semiOppositePenalty * SWmtn;
                    Wwater = Math.pow(distModifier, Wdist) * perpendiculars * Wmtn;
                    NWwater = Math.pow(distModifier, NWdist) * slantedWind * NWmtn;
                }
                else if (windDirection.equals("west")) {
                    Nwater = Math.pow(distModifier, Ndist) * perpendiculars * Nmtn;
                    NEwater = Math.pow(distModifier, NEdist) * semiOppositePenalty * NEmtn;
                    Ewater = Math.pow(distModifier, Edist) * oppositePenalty * Emtn;
                    SEwater = Math.pow(distModifier, SEdist) * semiOppositePenalty * SEmtn;
                    Swater = Math.pow(distModifier, Sdist) * perpendiculars * Smtn;
                    SWwater = Math.pow(distModifier, SWdist) * slantedWind * SWmtn;
                    Wwater = (Math.pow(distModifier, Wdist) + ((1 - Math.pow(distModifier, Wdist)) * Math.pow(windBonus, Wdist))) * Wmtn;
                    NWwater = Math.pow(distModifier, NWdist) * slantedWind * NWmtn;
                }
                else if (windDirection.equals("east")) {
                    Nwater = Math.pow(distModifier, Ndist) * perpendiculars * Nmtn;
                    NEwater = Math.pow(distModifier, NEdist) * slantedWind * NEmtn;
                    Ewater = (Math.pow(distModifier, Edist) + ((1 - Math.pow(distModifier, Edist)) * Math.pow(windBonus, Edist))) * Emtn;
                    SEwater = Math.pow(distModifier, SEdist) * slantedWind * SEmtn;
                    Swater = Math.pow(distModifier, Sdist) * perpendiculars * Smtn;
                    SWwater = Math.pow(distModifier, SWdist) * semiOppositePenalty * SWmtn;
                    Wwater = Math.pow(distModifier, Wdist) * oppositePenalty * Wmtn;
                    NWwater = Math.pow(distModifier, NWdist) * semiOppositePenalty * NWmtn;
                }
                else if (windDirection.equals("south")) {
                    Nwater = Math.pow(distModifier, Ndist) * oppositePenalty * Nmtn;
                    NEwater = Math.pow(distModifier, NEdist) * semiOppositePenalty * NEmtn;
                    Ewater = Math.pow(distModifier, Edist) * perpendiculars * Emtn;
                    SEwater = Math.pow(distModifier, SEdist) * slantedWind * SEmtn;
                    Swater = (Math.pow(distModifier, Sdist) + ((1 - Math.pow(distModifier, Sdist)) * Math.pow(windBonus, Sdist))) * Smtn;
                    SWwater = Math.pow(distModifier, SWdist) * slantedWind * SWmtn;
                    Wwater = Math.pow(distModifier, Wdist) * perpendiculars * Wmtn;
                    NWwater = Math.pow(distModifier, NWdist) * semiOppositePenalty * NWmtn;
                }
                else if (windDirection.equals("northwest")) {
                    Nwater = Math.pow(distModifier, Ndist) * slantedWind * Nmtn;
                    NEwater = Math.pow(distModifier, NEdist) * perpendiculars *NEmtn;
                    Ewater = Math.pow(distModifier, Edist) * semiOppositePenalty * Emtn;
                    SEwater = Math.pow(distModifier, SEdist) * oppositePenalty * SEmtn;
                    Swater = Math.pow(distModifier, Sdist) * semiOppositePenalty * Smtn;
                    SWwater = Math.pow(distModifier, SWdist) * perpendiculars * SWmtn;
                    Wwater = Math.pow(distModifier, Wdist) * slantedWind * Wmtn;
                    NWwater = (Math.pow(distModifier, NWdist) + ((1 - Math.pow(distModifier, NWdist)) * Math.pow(windBonus, NWdist))) * NWmtn;
                }
                else if (windDirection.equals("northeast")) {
                    Nwater = Math.pow(distModifier, Ndist) * slantedWind * Nmtn;
                    NEwater = (Math.pow(distModifier, NEdist) + ((1 - Math.pow(distModifier, NEdist)) * Math.pow(windBonus, NEdist))) * NEmtn;
                    Ewater = Math.pow(distModifier, Edist) * slantedWind * Emtn;
                    SEwater = Math.pow(distModifier, SEdist) * perpendiculars * SEmtn;
                    Swater = Math.pow(distModifier, Sdist) * semiOppositePenalty * Smtn;
                    SWwater = Math.pow(distModifier, SWdist) * oppositePenalty * SWmtn;
                    Wwater = Math.pow(distModifier, Wdist) * semiOppositePenalty * Wmtn;
                    NWwater = Math.pow(distModifier, NWdist) * perpendiculars * NWmtn;
                }
                else if (windDirection.equals("southwest")) {
                    Nwater = Math.pow(distModifier, Ndist) * semiOppositePenalty * Nmtn;
                    NEwater = Math.pow(distModifier, NEdist) * oppositePenalty * NEmtn;
                    Ewater = Math.pow(distModifier, Edist) * semiOppositePenalty * Emtn;
                    SEwater = Math.pow(distModifier, SEdist) * perpendiculars * SEmtn;
                    Swater = Math.pow(distModifier, Sdist) * slantedWind * Smtn;
                    SWwater = (Math.pow(distModifier, SWdist) + ((1 - Math.pow(distModifier, SWdist)) * Math.pow(windBonus, SWdist))) * SWmtn;
                    Wwater = Math.pow(distModifier, Wdist) * slantedWind * Wmtn;
                    NWwater = Math.pow(distModifier, NWdist) * perpendiculars * NWmtn;
                }
                else if (windDirection.equals("southeast")) {
                    Nwater = Math.pow(distModifier, Ndist) * semiOppositePenalty * Nmtn;
                    NEwater = Math.pow(distModifier, NEdist) * perpendiculars * NEmtn;
                    Ewater = Math.pow(distModifier, Edist) * slantedWind * Emtn;
                    SEwater = (Math.pow(distModifier, SEdist) + ((1 - Math.pow(distModifier, SEdist)) * Math.pow(windBonus, SEdist))) * SEmtn;
                    Swater = Math.pow(distModifier, Sdist) * slantedWind * Smtn;
                    SWwater = Math.pow(distModifier, SWdist) * perpendiculars * SWmtn;
                    Wwater = Math.pow(distModifier, Wdist) * semiOppositePenalty * Wmtn;
                    NWwater = Math.pow(distModifier, NWdist) * oppositePenalty * NWmtn;
                }

                double waterSource = Math.max(Nwater, Math.max(NEwater, Math.max(Ewater, Math.max(SEwater, Math.max(Swater, Math.max(SWwater, Math.max(Wwater, NWwater)))))));

                //FINAL RIVER & LAKE MODIFIERS
                if (NdistLake > 2.9 && NdistLake < 3.1 || NEdistLake > 4.1 && NEdistLake < 4.3 || EdistLake > 2.9 && EdistLake < 3.1 || SEdistLake > 4.1 && SEdistLake < 4.3 || SdistLake > 2.9 && SdistLake < 3.1 || SWdistLake > 4.1 && SWdistLake < 4.3 || WdistLake > 2.9 && WdistLake < 3.1 || NWdistLake > 4.1 && NWdistLake < 4.3) {
                    waterSource = (waterSource + ((1 - waterSource) * .20));
                }
                else if (NdistLake > 3.9 && NdistLake < 4.1 || NEdistLake > 5.5 && NEdistLake < 5.7 || EdistLake > 3.9 && EdistLake < 4.1 || SEdistLake > 5.5 && SEdistLake < 5.7 || SdistLake > 3.9 && SdistLake < 4.1 || SWdistLake > 5.5 && SWdistLake < 5.7 || WdistLake > 3.9 && WdistLake < 4.1|| NWdistLake > 5.5 && NWdistLake < 5.7) {
                    waterSource = (waterSource + ((1 - waterSource) * .10));
                }

                //EQUATOR/POLES SHIT
                double precipMax = (waterSource + ((1-waterSource) * .05));
                double precipMin = (waterSource * .15);
                double precipRange = precipMax - precipMin;
                double equatorR = cellRowCount/2, maxDist = cellRowCount/2;
                double distanceToEQ = 0;
                if (r >= equatorR) {distanceToEQ = r - equatorR;}
                else if (r <= equatorR) {distanceToEQ = equatorR - r;}
                grid[c][r].water = (precipMax - (precipRange * (distanceToEQ/maxDist)));

                if (grid[c][r].color == freshBlue || grid[c][r].color.equals(oceanBlue)) {grid[c][r].water = 1;}
            }
        }
        averagePrecip();
        averagePrecip();
        averagePrecip();
        averagePrecip();
        defineHeat();
        if(windComesFrom == null) {windComesFrom = "west";}
        else if(windComesFrom.equals("west")) {windComesFrom = "northwest";}
        else if(windComesFrom.equals("northwest")) {windComesFrom = "north";}
        else if(windComesFrom.equals("north")) {windComesFrom = "northeast";}
        else if(windComesFrom.equals("northeast")) {windComesFrom = "east";}
        else if(windComesFrom.equals("east")) {windComesFrom = "southeast";}
        else if(windComesFrom.equals("southeast")) {windComesFrom = "south";}
        else if(windComesFrom.equals("south")) {windComesFrom = "southwest";}
        else if(windComesFrom.equals("southwest")) {windComesFrom = "west";}
    }

    public void averagePrecip() {
        for(int i = 0; i < 2; i++) {
            for (int c = 0; c < cellColumnCount; c++) {
                for (int r = 0; r < cellRowCount; r++) {
                    int neighborCount = grid[c][r].getNeighborCount();
                    GridCell[] tempNeighbors = grid[c][r].getNeighbors();
                    double precipAvg = grid[c][r].water;
                    for (int n = 0; n < neighborCount; n++) {
                        precipAvg += tempNeighbors[n].water;
                        grid[c][r].nextWater = (precipAvg / (neighborCount + 1));
                    }
                }
            }
            for (int c = 0; c < cellColumnCount; c++) {
                for (int r = 0; r < cellRowCount; r++) {
                    grid[c][r].water = grid[c][r].nextWater;
                }
            }
        }
        for(int c = 0; c < cellColumnCount; c++) {
            for(int r = 0; r < cellRowCount; r++) {
                if (grid[c][r].color == freshBlue || grid[c][r].color.equals(oceanBlue)) {grid[c][r].water = 1;}
            }
        }
    }

    public void defineHeat(){
        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                double heatMax = 90;
                double heatMin = 30;
                double heatRange = heatMax - heatMin;
                double equatorR = cellRowCount/2, maxDist = cellRowCount/2;
                double distanceToEQ = 0;
                if (r >= equatorR) {distanceToEQ = r - equatorR;}
                else if (r <= equatorR) {distanceToEQ = equatorR - r;}
                double heatAvg = (heatMax - (heatRange * (distanceToEQ/maxDist)));
                if (grid[c][r].color == mountainWhite) {heatAvg -= 30;}

                boolean Ncheck = false, NEcheck = false, Echeck = false, SEcheck = false, Scheck = false, SWcheck = false, Wcheck = false, NWcheck = false;
                double Ndist = 0, NEdist = 0, Edist = 0, SEdist = 0, Sdist = 0, SWdist = 0, Wdist = 0, NWdist = 0;
                int cloneC = c;
                int cloneR = r;

                while (Ncheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        Ndist++;
                        r--;
                        if (r < 0) {break;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        Ncheck = true;
                    }
                }
                c = cloneC;
                r = cloneR;
                while (Wcheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        Wdist++;
                        c--;
                        if (c < 0) {c = (cellColumnCount-1);}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        Wcheck = true;
                    }
                }
                c = cloneC;
                r = cloneR;
                while (Scheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        Sdist++;
                        r++;
                        if (r >= cellRowCount) {break;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        Scheck = true;
                    }
                }
                c = cloneC;
                r = cloneR;
                while (Echeck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        Edist++;
                        c++;
                        if (c >= cellColumnCount) {c = 0;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        Echeck = true;
                    }
                }
                c = cloneC;
                r = cloneR;
                while (NEcheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        NEdist += 1.4;
                        r--;
                        c++;
                        if (c >= cellColumnCount) {c = 0;}
                        if (r < 0) {break;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        NEcheck = true;
                    }
                }
                c = cloneC;
                r = cloneR;
                while (NWcheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        NWdist += 1.4;
                        r--;
                        c--;
                        if (c < 0) {c = (cellColumnCount-1);}
                        if (r < 0) {break;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        NWcheck = true;
                    }
                }
                c = cloneC;
                r = cloneR;
                while (SEcheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        SEdist += 1.4;
                        r++;
                        c++;
                        if (c >= cellColumnCount) {c = 0;}
                        if (r >= cellRowCount) {break;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        SEcheck = true;
                    }
                }
                c = cloneC;
                r = cloneR;
                while (SWcheck == false) {
                    if (grid[c][r].color == landGreen || grid[c][r].color == freshBlue || grid[c][r].color == mountainWhite) {
                        SWdist += 1.4;
                        r++;
                        c--;
                        if (c < 0) {c = (cellColumnCount-1);}
                        if (r >= cellRowCount) {break;}
                    }
                    else if (grid[c][r].color.equals(oceanBlue)) {
                        SWcheck = true;
                    }
                }
                c = cloneC;
                r = cloneR;

                double minDist = Math.min(Ndist, Math.min(NEdist, Math.min(Edist, Math.min(SEdist, Math.min(Sdist, Math.min(SWdist, Math.min(Wdist, NWdist)))))));
                double waterHalfRange = (1 - grid[c][r].water) * 85;
                double distHalfRange = (1 - (Math.pow(.98, minDist))) * 85;
                double finalRange = ((waterHalfRange * 2)+(distHalfRange * 2))/2;
                double maxTempRange = 0;

                if (grid[c][r].water > .75) {maxTempRange = (finalRange - (finalRange * grid[c][r].water));}
                else if (grid[c][r].water <= .75) {maxTempRange = (finalRange - (finalRange * .75));}
                if (!grid[c][r].color.equals(oceanBlue)) {
                    grid[c][r].maxTemp = Math.round(heatAvg + maxTempRange);
                    grid[c][r].minTemp = Math.round(grid[c][r].maxTemp - finalRange);
                    grid[c][r].avgTemp = Math.round((grid[c][r].maxTemp + grid[c][r].minTemp) / 2);
                }
                else if (grid[c][r].color.equals(oceanBlue)) {
                    grid[c][r].maxTemp = Math.round(heatAvg + 5);
                    grid[c][r].minTemp = Math.round(heatAvg - 5);
                    grid[c][r].avgTemp = Math.round(heatAvg);
                }
            }
        }
        averageHeat();
        averageHeat();
        averageHeat();

        globalAvgHigh = -1000;
        globalAvgLow = 1000;
        globalMaxHigh = -1000;
        globalMaxLow = 1000;
        globalMinHigh = -1000;
        globalMinLow = 1000;

        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                double cellAvg = Math.round((grid[c][r].maxTemp + grid[c][r].minTemp)/2);
                if (cellAvg > globalAvgHigh) {globalAvgHigh = cellAvg;}
                else if (cellAvg < globalAvgLow) {globalAvgLow = cellAvg;}
                if (grid[c][r].maxTemp > globalMaxHigh) {globalMaxHigh = grid[c][r].maxTemp;}
                else if (grid[c][r].maxTemp < globalMaxLow) {globalMaxLow = grid[c][r].maxTemp;}
                if (grid[c][r].minTemp > globalMinHigh) {globalMinHigh = grid[c][r].minTemp;}
                else if (grid[c][r].minTemp < globalMinLow) {globalMinLow = grid[c][r].minTemp;}
            }
        }
        /*System.out.println(globalAvgHigh);
        System.out.println(globalAvgLow);
        System.out.println(globalMaxHigh);
        System.out.println(globalMaxLow);
        System.out.println(globalMinHigh);
        System.out.println(globalMinLow);*/
    }

    public void averageHeat() {
        for(int i = 0; i < 2; i++) {
            for(int c=0; c<cellColumnCount; c++) {
                for(int r=0; r<cellRowCount; r++) {
                    int neighborCount = grid[c][r].getNeighborCount();
                    GridCell[] tempNeighbors = grid[c][r].getNeighbors();

                    double maxTempAvg = grid[c][r].maxTemp;
                    double minTempAvg = grid[c][r].minTemp;
                    for (int n = 0; n < neighborCount; n++) {
                        maxTempAvg += tempNeighbors[n].maxTemp;
                        grid[c][r].nextMaxTemp = Math.round(maxTempAvg/(neighborCount + 1));
                        minTempAvg += tempNeighbors[n].minTemp;
                        grid[c][r].nextMinTemp = Math.round(minTempAvg/(neighborCount + 1));
                        grid[c][r].nextAvgTemp = Math.round((grid[c][r].maxTemp + grid[c][r].minTemp)/2);
                    }
                }
            }
            for (int c = 0; c < cellColumnCount; c++) {
                for (int r = 0; r < cellRowCount; r++) {
                    grid[c][r].maxTemp = grid[c][r].nextMaxTemp;
                    grid[c][r].minTemp = grid[c][r].nextMinTemp;
                    grid[c][r].avgTemp = grid[c][r].nextAvgTemp;
                }
            }
        }
    }

    public void drawPrecipMap() {
        biomeFlag = false; maxTempFlag = false; minTempFlag = false; maxRangeFlag = false; earthFlag = false; precipFlag = false; avgTempFlag = false;
        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                if (grid[c][r].color == freshBlue || grid[c][r].color.equals(oceanBlue)) {
                    grid[c][r].color = oceanBlue;
                }
                else if (grid[c][r].water >= .666) {
                    int mapColor = (int)Math.floor(255 * (grid[c][r].water - .666) * 3);
                    grid[c][r].color = new Color(0, 255, mapColor);
                }
                else if (grid[c][r].water >= .333 && grid[c][r].water < .666) {
                    int mapColor = (int)Math.floor(255 * ((.666 - grid[c][r].water)/.333));
                    grid[c][r].color = new Color(mapColor, 255, 0);
                }
                else if (grid[c][r].water < .333) {
                    int mapColor = (int)Math.floor(255 * (grid[c][r].water/.333));
                    grid[c][r].color = new Color(255, mapColor, 0);
                }
            }
        }
        precipFlag = true;
        repaint();
    }

    public void drawMaxTempMap() {
        biomeFlag = false; maxTempFlag = false; minTempFlag = false; maxRangeFlag = false; earthFlag = false; precipFlag = false; avgTempFlag = false;
        double worldTempHighest = -1000;
        double worldTempLowest = 1000;
        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                if (grid[c][r].maxTemp > worldTempHighest && !grid[c][r].color.equals(oceanBlue)) {worldTempHighest = grid[c][r].maxTemp;}
                if (grid[c][r].maxTemp < worldTempLowest && !grid[c][r].color.equals(oceanBlue)) {worldTempLowest = grid[c][r].maxTemp;}
            }
        }
        //System.out.println("High: " + worldTempHighest + " Low: " + worldTempLowest);
        double tempRange = worldTempHighest - worldTempLowest;

        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                if (grid[c][r].color == freshBlue || grid[c][r].color.equals(oceanBlue)) {
                    grid[c][r].color = oceanBlue;
                }
                else if (((worldTempHighest - grid[c][r].maxTemp)/tempRange) < .333) {
                    int mapColor = (int)Math.floor(255 * (((worldTempHighest - grid[c][r].maxTemp)/tempRange))/.333);
                    /*System.out.println("C: " + c + " R: " + r);
                    System.out.println(worldTempHighest + " " + grid[c][r].maxTemp + " " + tempRange);
                    System.out.println(mapColor);*/
                    grid[c][r].color = new Color(255, mapColor, 0);
                }
                else if (((worldTempHighest - grid[c][r].maxTemp)/tempRange) >= .333 && ((worldTempHighest - grid[c][r].maxTemp)/tempRange) < .666) {
                    int mapColor = (int)Math.floor(255 * (((.666 - (worldTempHighest - grid[c][r].maxTemp)/tempRange))/.333));
                    /*System.out.println("C: " + c + " R: " + r);
                    System.out.println(worldTempHighest + " " + grid[c][r].maxTemp + " " + tempRange);
                    System.out.println(mapColor);*/
                    grid[c][r].color = new Color(mapColor, 255, 0);
                }
                else if (((worldTempHighest - grid[c][r].maxTemp)/tempRange) >= .666) {
                    int mapColor = (int)Math.floor(255 * (((worldTempHighest - grid[c][r].maxTemp)/tempRange) - .666) * 3);
                    /*System.out.println("C: " + c + " R: " + r);
                    System.out.println(worldTempHighest + " " + grid[c][r].maxTemp + " " + tempRange);
                    System.out.println(mapColor);*/
                    grid[c][r].color = new Color(0, 255,mapColor);
                }
            }
        }
        maxTempFlag = true;
        repaint();
    }

    public void drawMinTemp() {
        biomeFlag = false; maxTempFlag = false; minTempFlag = false; maxRangeFlag = false; earthFlag = false; precipFlag = false; avgTempFlag = false;
        double worldTempHighest = 0;
        double worldTempLowest = 0;
        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                if (grid[c][r].minTemp > worldTempHighest) {worldTempHighest = grid[c][r].minTemp;}
                if (grid[c][r].minTemp < worldTempLowest) {worldTempLowest = grid[c][r].minTemp;}
            }
        }

        double tempRange = worldTempHighest - worldTempLowest;

        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                if (grid[c][r].color == freshBlue || grid[c][r].color.equals(oceanBlue)) {
                    grid[c][r].color = oceanBlue;
                }
                else if (((worldTempHighest - grid[c][r].minTemp)/tempRange) < .33) {
                    int mapColor = (int)Math.floor(255 * (((worldTempHighest - grid[c][r].minTemp)/tempRange))/.33);
                    grid[c][r].color = new Color(255,mapColor, 0);
                }
                else if (((worldTempHighest - grid[c][r].minTemp)/tempRange) >= .33 && ((worldTempHighest - grid[c][r].minTemp)/tempRange) < .67) {
                    int mapColor = (int)Math.floor(255 * (((.67 - (worldTempHighest - grid[c][r].minTemp)/tempRange))/.34));
                    grid[c][r].color = new Color(mapColor, 255, 0);
                }
                else if (((worldTempHighest - grid[c][r].minTemp)/tempRange) >= .67 && ((worldTempHighest - grid[c][r].minTemp)/tempRange) <= 1) {
                    int mapColor = (int)Math.floor(255 * (((worldTempHighest - grid[c][r].minTemp)/tempRange) - .67)*3);
                    grid[c][r].color = new Color(0, 255, mapColor);
                }
            }
        }
        minTempFlag = true;
        repaint();
    }

    public void drawAvgTempMap() {
        biomeFlag = false; maxTempFlag = false; minTempFlag = false; maxRangeFlag = false; earthFlag = false; precipFlag = false; avgTempFlag = false;
        double worldTempHighest = 0;
        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                if (((grid[c][r].maxTemp + grid[c][r].minTemp) / 2) > worldTempHighest) {worldTempHighest = ((grid[c][r].maxTemp + grid[c][r].minTemp) / 2);}
            }
        }

        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                double cellAvg = ((grid[c][r].maxTemp + grid[c][r].minTemp) / 2);
                if (grid[c][r].color == freshBlue || grid[c][r].color.equals(oceanBlue)) {
                    grid[c][r].color = oceanBlue;
                }
                else if ((cellAvg/worldTempHighest) >= .666) {
                    int mapColor = (int)Math.floor(255 * (1 - (cellAvg/worldTempHighest)) / .333);
                    grid[c][r].color = new Color(255, mapColor, 0);
                }
                else if ((cellAvg/worldTempHighest) >= .333 && (cellAvg/worldTempHighest) < .666) {
                    int mapColor = (int)Math.floor(255 * ((cellAvg/worldTempHighest) - .333) * 3);
                    grid[c][r].color = new Color(mapColor, 255, 0);
                }
                else if ((cellAvg/worldTempHighest) < .333) {
                    int mapColor = (int)Math.floor(255 * (.333 - (cellAvg/worldTempHighest)) / .333);
                    if (mapColor > 255) {mapColor = 255;}
                    grid[c][r].color = new Color(0, 255, mapColor);
                }
            }
        }
        avgTempFlag = true;
        repaint();
    }

    public void drawMaxRangeMap() {
        biomeFlag = false; maxTempFlag = false; minTempFlag = false; maxRangeFlag = false; earthFlag = false; precipFlag = false; avgTempFlag = false;
        double worldHighestRange = 0;
        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                if ((grid[c][r].maxTemp - grid[c][r].minTemp) > worldHighestRange) {worldHighestRange = (grid[c][r].maxTemp - grid[c][r].minTemp);}
            }
        }

        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                double cellRange = (grid[c][r].maxTemp - grid[c][r].minTemp);
                if (grid[c][r].color == freshBlue || grid[c][r].color.equals(oceanBlue)) {
                    grid[c][r].color = oceanBlue;
                }
                else if ((cellRange/worldHighestRange) >= .666) {
                    int mapColor = (int)Math.floor(255 * (1 - (cellRange/worldHighestRange)) / .333);
                    grid[c][r].color = new Color(255,mapColor, 0);
                }
                else if ((cellRange/worldHighestRange) >= .333 && (cellRange/worldHighestRange) < .666) {
                    int mapColor = (int)Math.floor(255 * ((cellRange/worldHighestRange) - .333) * 3);
                    grid[c][r].color = new Color(mapColor, 255, 0);
                }
                else if ((cellRange/worldHighestRange < .333)) {
                    int mapColor = (int)Math.floor(255 * (.333 - (cellRange/worldHighestRange)) / .333);
                    grid[c][r].color = new Color(0, 255, mapColor);
                }
            }
        }
        maxRangeFlag = true;
        repaint();
    }

    public void drawBiomes() {
        biomeFlag = false; maxTempFlag = false; minTempFlag = false; maxRangeFlag = false; earthFlag = false; precipFlag = false; avgTempFlag = false;
        int mtnCount = 0, tundraCount = 0, desertCount = 0, temperateGrassCount = 0, savannaCount = 0, taigaCount = 0, temperateDeciduousCount = 0, tropicSeasonalCount = 0, temperateRainCount = 0, rainforestCount = 0;
        for(int c=0; c<cellColumnCount; c++) {
            for(int r=0; r<cellRowCount; r++) {
                if (grid[c][r].color == freshBlue || grid[c][r].color.equals(oceanBlue) || grid[c][r].color.equals(Color.red)) {   //DELETE RED EVENTUALLY
                    grid[c][r].color = oceanBlue;
                }
                else if (grid[c][r].trueColor == mountainWhite) {
                    grid[c][r].color = mountainWhite;
                    mtnCount++;
                }
                else if (grid[c][r].avgTemp <= 25) {
                    grid[c][r].color = tundra;
                    tundraCount++;
                }
                else if (grid[c][r].avgTemp < 1000 && grid[c][r].avgTemp > 25 && grid[c][r].water < ((.0029762 * grid[c][r].avgTemp) - .0685)) {
                    grid[c][r].color = desert;
                    desertCount++;
                }
                else if (grid[c][r].avgTemp < 63 && grid[c][r].avgTemp > 25 && grid[c][r].water < ((.0069444 * grid[c][r].avgTemp) - .1597)) {
                    grid[c][r].color = temperateGrassland;
                    temperateGrassCount++;
                }
                else if (grid[c][r].avgTemp < 1000 && grid[c][r].avgTemp >= 63 && grid[c][r].water < ((.0069444 * grid[c][r].avgTemp) - .1597)) {
                    grid[c][r].color = savanna;
                    savannaCount++;
                }
                else if (grid[c][r].avgTemp < 37 && grid[c][r].avgTemp > 25) {
                    grid[c][r].color = taiga;
                    taigaCount++;
                }
                else if (grid[c][r].avgTemp < 63 && grid[c][r].avgTemp >= 37 && grid[c][r].water < ((.0050778 * grid[c][r].avgTemp) + .2293)) {
                    grid[c][r].color = temperateDeciduous;
                    temperateDeciduousCount++;
                }
                else if (grid[c][r].avgTemp < 1000 && grid[c][r].avgTemp >= 63 && grid[c][r].water < ((.0050778 * grid[c][r].avgTemp) + .2293)) {
                    grid[c][r].color = tropicalSeasonalRain;
                    tropicSeasonalCount++;
                }
                else if (grid[c][r].avgTemp < 63 && grid[c][r].avgTemp >= 37) {
                    grid[c][r].color = temperateRainforest;
                    temperateRainCount++;
                }
                else if (grid[c][r].avgTemp < 1000 && grid[c][r].avgTemp >= 63) {
                    grid[c][r].color = tropicalRainforest;
                    rainforestCount++;
                }
            }
        }
        biomeFlag = true;
        repaint();
    }

    public void drawEarthMap() {
        biomeFlag = false; maxTempFlag = false; minTempFlag = false; maxRangeFlag = false; earthFlag = false; precipFlag = false; avgTempFlag = false;
        for(int c=0; c<cellColumnCount; c++) {
            for (int r = 0; r < cellRowCount; r++) {
                grid[c][r].color = grid[c][r].trueColor;
            }
        }
        earthFlag = true;
        repaint();
    }

    public void storeTrueColor() {
        for(int c=0; c<cellColumnCount; c++) {
            for (int r = 0; r < cellRowCount; r++) {
                grid[c][r].trueColor = grid[c][r].color;
            }
        }
    }

    public void paintComponent(Graphics g){
        setBackground(Color.BLACK);
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g);
        for(int c=0; c<cellColumnCount; c++) {
            for (int r = 0; r < cellRowCount; r++) {
                g.setColor(grid[c][r].color);
                g.fillRect(grid[c][r].getX(), grid[c][r].getY(), cellWidth, cellHeight);
            }
        }
        final int SIZE12 = 12;
        Font gameFont = new Font("SansSerif", Font.PLAIN, SIZE12);
        g.setFont(gameFont);
        g.setColor(Color.white);

        g.drawString("(E)arth View", 5, 578);
        g.drawString("(B)iomes", 165, 578);
        g.drawString("(W)ater Availability", 165, 593);
        g.drawString("(R)ange of Temperature", 300, 578);
        g.drawString("(A)verage Temperature", 300, 593);
        g.drawString("(H)ighest Temperature", 465, 578);
        g.drawString("(L)owest Temperature", 465, 593);
        if(windComesFrom.equals("west")) {
            g.drawString("(D)irection of Wind: E", 5, 593);
        }
        else if(windComesFrom.equals("northwest")) {
            g.drawString("(D)irection of Wind: SE", 5, 593);
        }
        else if(windComesFrom.equals("north")) {
            g.drawString("(D)irection of Wind: S", 5, 593);
        }
        else if(windComesFrom.equals("northeast")) {
            g.drawString("(D)irection of Wind: SW", 5, 593);
        }
        else if(windComesFrom.equals("east")) {
            g.drawString("(D)irection of Wind: W", 5, 593);
        }
        else if(windComesFrom.equals("southeast")) {
            g.drawString("(D)irection of Wind: NW", 5, 593);
        }
        else if(windComesFrom.equals("south")) {
            g.drawString("(D)irection of Wind: N", 5, 593);
        }
        else if(windComesFrom.equals("southwest")) {
            g.drawString("(D)irection of Wind: NE", 5, 593);
        }
        if(biomeFlag) {
            g.drawString("Tropical Rainforest", 25, 612);
            g.setColor(tropicalRainforest);
            g.fillRect(7, 600, 14, 14);

            g.setColor(Color.white);
            g.drawString("Tropical Seasonal Rainforest", 160, 612);
            g.setColor(tropicalSeasonalRain);
            g.fillRect(142, 600, 14, 14);

            g.setColor(Color.white);
            g.drawString("Savanna", 350, 612);
            g.setColor(savanna);
            g.fillRect(332, 600, 14, 14);

            g.setColor(Color.white);
            g.drawString("Desert", 434, 612);
            g.setColor(desert);
            g.fillRect(414, 600, 14, 14);

            g.setColor(Color.white);
            g.drawString("Taiga", 505, 612);
            g.setColor(taiga);
            g.fillRect(487, 600, 14, 14);

            g.setColor(Color.white);
            g.drawString("Tundra", 568, 612);
            g.setColor(tundra);
            g.fillRect(550, 600, 14, 14);

            g.setColor(Color.white);
            g.drawString("Alpine", 525, 632);
            g.setColor(mountainWhite);
            g.fillRect(507, 620, 14, 14);

            g.setColor(Color.white);
            g.drawString("Temperate Rainforest", 25, 632);
            g.setColor(temperateRainforest);
            g.fillRect(7, 620, 14, 14);

            g.setColor(Color.white);
            g.drawString("Temperate Deciduous Forest", 176, 632);
            g.setColor(temperateDeciduous);
            g.fillRect(158, 620, 14, 14);

            g.setColor(Color.white);
            g.drawString("Temperate Grasslands", 368, 632);
            g.setColor(temperateGrassland);
            g.fillRect(348, 620, 14, 14);
        }
        if(maxRangeFlag) {
            g.setColor(Color.cyan);
            g.fillRect(7, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString("Stable Temperatures", 25, 612);

            g.setColor(Color.green);
            g.fillRect(152, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString("Light Fluctuation", 170, 612);

            g.setColor(Color.yellow);
            g.fillRect(274, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString("Moderate Fluctuation", 292, 612);

            g.setColor(Color.red);
            g.fillRect(421, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString("Extreme Fluctuation", 439, 612);
        }
        if(maxTempFlag) {
            g.setColor(Color.cyan);
            g.fillRect(7, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString(Math.round(globalMaxLow) + "°F", 25, 612);

            g.setColor(Color.green);
            g.fillRect(85, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString(Math.round(((globalMaxHigh-globalMaxLow) * .33) + globalMaxLow) + "°F", 103, 612);

            g.setColor(Color.yellow);
            g.fillRect(163, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString(Math.round(((globalMaxHigh-globalMaxLow) * .67) + globalMaxLow) + "°F", 181, 612);

            g.setColor(Color.red);
            g.fillRect(241, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString(Math.round(globalMaxHigh) + "°F", 259, 612);
        }
        if(minTempFlag) {
            g.setColor(Color.cyan);
            g.fillRect(7, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString(Math.round(globalMinLow) + "°F", 25, 612);

            g.setColor(Color.green);
            g.fillRect(85, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString(Math.round(((globalMinHigh-globalMinLow) * .33) + globalMinLow) + "°F", 103, 612);

            g.setColor(Color.yellow);
            g.fillRect(163, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString(Math.round(((globalMinHigh-globalMinLow) * .67) + globalMinLow) + "°F", 181, 612);

            g.setColor(Color.red);
            g.fillRect(241, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString(Math.round(globalMinHigh) + "°F", 259, 612);
        }
        if(avgTempFlag) {
            g.setColor(Color.cyan);
            g.fillRect(7, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString(Math.round(globalAvgLow) + "°F", 25, 612);

            g.setColor(Color.green);
            g.fillRect(85, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString(Math.round(((globalAvgHigh-globalAvgLow) * .33) + globalAvgLow) + "°F", 103, 612);

            g.setColor(Color.yellow);
            g.fillRect(163, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString(Math.round(((globalAvgHigh-globalAvgLow) * .67) + globalAvgLow) + "°F", 181, 612);

            g.setColor(Color.red);
            g.fillRect(241, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString(Math.round(globalAvgHigh) + "°F", 259, 612);
        }
        if(precipFlag) {
            g.setColor(Color.cyan);
            g.fillRect(7, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString("100%", 25, 612);

            g.setColor(Color.green);
            g.fillRect(85, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString("67%", 103, 612);

            g.setColor(Color.yellow);
            g.fillRect(163, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString("33%", 181, 612);

            g.setColor(Color.red);
            g.fillRect(241, 600, 14, 14);
            g.setColor(Color.white);
            g.drawString("0%", 259, 612);
        }
    }
}