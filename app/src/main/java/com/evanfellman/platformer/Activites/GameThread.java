package com.evanfellman.platformer.Activites;

import android.view.SurfaceHolder;

class GameThread extends Thread {

    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    public GameThread(){
        super();
    }


    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder)  {
        super();
//        this.gameSurface= gameSurface;
//        this.surfaceHolder= surfaceHolder;
    }

    @Override
    public void run()  {
//        long startTime = System.currentTimeMillis();
//
//        while(running)  {
//            System.out.println("updating visuals!");
//            Canvas canvas= new Canvas();
//            try {
//                // Get Canvas from Holder and lock it.
//                canvas = this.surfaceHolder.lockCanvas(null);
////                this.gameSurface.draw(canvas);
//                // Synchronized
//                synchronized (canvas)  {
////                    this.gameSurface.update(canvas);
//
//                }
//            }catch(Exception e)  {
//                e.printStackTrace();
//                System.out.println("oopsss");
//                // Do nothing.
//            } finally {
//                if(canvas!= null)  {
//                    // Unlock Canvas.
//                    this.surfaceHolder.unlockCanvasAndPost(canvas);
//                }
//            }
//            System.out.println("got herereeee");
////            long now = System.nanoTime() ;
//            // Interval to redraw game
//            // (Change nanoseconds to milliseconds)
//            long now = System.currentTimeMillis();
//            long waitTime = (now - startTime)/100;
//            if(waitTime < 10)  {
//                waitTime= 10; // Millisecond.
//            }
//            System.out.print(" Wait Time="+ waitTime);
//
//            try {
//                // Sleep.
//                this.sleep(waitTime);
//            } catch(InterruptedException e)  {
//
//            }
//            startTime = System.nanoTime();
//            System.out.print(".");
//        }
    }

//    public void setRunning(boolean running)  {
//        this.running= running;
//    }
}

//class DrawThread extends Thread {
//    private CustomView cv;
//    private SurfaceHolder surfaceHolder;

//    DrawThread(CustomView cv, SurfaceHolder surfaceHolder) {
////        this.cv = cv;
////        this.surfaceHolder = surfaceHolder;
//    }
//
//    public void run() {
//        Canvas canvas;
//        while (true) {
////            startTime = System.nanoTime();
//            canvas = null;
//
//            try {
//                canvas = this.surfaceHolder.lockCanvas();
//                synchronized(surfaceHolder) {
////                    canvas = CustomView.drawMe();
//                    this.cv.draw(canvas);
//                }
//            } catch (Exception e) {       }
//            finally {
//                if (true || canvas != null){
//                    try {
//                        surfaceHolder.unlockCanvasAndPost(canvas);
//                    }
//                    catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
////            timeMillis = (System.nanoTime() - startTime) / 1000000;
////            waitTime = targetTime - timeMillis;
//
////            try {
////                this.sleep(waitTime);
////            } catch (Exception e) {}
//
////            totalTime += System.nanoTime() - startTime;
////            frameCount++;
////            if (frameCount == targetFPS)        {
////                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
////                frameCount = 0;
////                totalTime = 0;
////                System.out.println(averageFPS);
////            }
//        }
////        while (true) {
////            CustomView.canvas = null;
////            System.out.println("in while loop thread thing");
////            try {
////                CustomView.canvas = this.surfaceHolder.lockCanvas();
////                synchronized(surfaceHolder) {
////                    CustomView.canvas.drawARGB(255, 255, 0, 0);
////                    this.cv.draw(CustomView.canvas);
////                }
////            } catch (Exception e) {} finally {
////                if (CustomView.canvas != null) {
////                    try {
////                        surfaceHolder.unlockCanvasAndPost(CustomView.canvas);
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                }
////            }
////
////        }
////        System.out.println("hi");
////        System.out.println(cv.levelNum);
////        MainActivity.upPressed = PlayCustomLevelActivity.upBtn.isPressed();
////        MainActivity.leftPressed = PlayCustomLevelActivity.leftBtn.isPressed();
////        MainActivity.rightPressed = PlayCustomLevelActivity.rightBtn.isPressed();
////        Canvas c = new Canvas();
////        Paint p = new Paint();
////        p.setARGB(255, 128, 0, 0);
////        c.drawRect(new Rect(0, 0, 100, 500), p);
////        cv.draw(c);
////        for(int i = -1 * canvas.getWidth(); i < canvas.getWidth(); i++){
////            for(int j = -1 * canvas.getHeight(); j < canvas.getHeight(); j++){
////                ArrayList<Thing> listOfThings = MainActivity.getFromLevel(i, j);
////                for(Thing t: listOfThings){
////                    if(t.id.equals("player")){
////                        continue;
////                    } else {
////                        t.display(canvas, MainActivity.cameraX, MainActivity.cameraY);
////                    }
////                }
////            }
////        }
////        System.out.println("got here");
////        while(true) {
////            Canvas c = new Canvas();
////            Paint p = new Paint();
////            p.setARGB(255, 128, 0, 0);
////            c.drawRect(new Rect(0, 0, 100, 500), p);
////
////            c.drawColor(Color.RED);
////            cv.draw(c);
////            try {
////                Thread.sleep(1000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////            System.out.println("hi");
//////            cv.drawMe();
////            Canvas c = new Canvas();
////            c.drawRect(new Re);
////        }
//    }
//}
