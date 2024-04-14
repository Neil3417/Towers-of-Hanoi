public class Lab4
{
public static void loadTest1()
{
Robot.load("test1.txt");
Robot.setDelay(1);
Lab4.nextBase();
}
public static void loadTest2()
{
Robot.load("test2.txt");
Robot.setDelay(0);
Lab4.nextBase();
}
public static void turnAround()
{
Robot.turnLeft();
Robot.turnLeft();
}
public static void backUp()
{
turnAround();
Robot.move();
turnAround();
}
public static void turnRight()
{
int turnsMade;
turnsMade = 0;
while ( turnsMade < 3 )
{
Robot.turnLeft();
turnsMade = turnsMade + 1;
}
}
public static void moveToWall()
{
while ( Robot.frontIsClear() )
{
Robot.move();
}
}
public static void moveToLight()
{
while ( Robot.onDark() )
{
Robot.move();
}
}
public static boolean robotFrontIsDark(){
Robot.move();
if(Robot.onDark()){
Lab4.backUp();
return true;
}
else{
Lab4.backUp();
return false;
}
}
public static boolean frontLeftIsWall(){
Robot.move();
Robot.turnLeft();
if(!Robot.frontIsClear()){
Lab4.turnRight();
Lab4.backUp();
return true;
}
else{
Lab4.turnRight();
Lab4.backUp();
return false;
}
}
public static boolean frontRightIsWall(){
Robot.move();
Lab4.turnRight();
if(!Robot.frontIsClear()){
Robot.turnLeft();
Lab4.backUp();
return true;
}
else{
Robot.turnLeft();
Lab4.backUp();
return false;
}
}
public static void robotReturn(){
Lab4.turnAround();
Lab4.moveToWall();
Lab4.turnAround();
}
public static void robotReturnStartPoint(){
Lab4.turnRight();
if(Robot.frontIsClear()){
if(!Lab4.frontRightIsWall()){
Lab4.moveToWall();
}
}
Lab4.turnRight();
while(Lab4.frontLeftIsWall()){
Robot.move();
}
Lab4.turnRight();
}
//All of the methods below require (1) that the robot is facing north and
//(2) that the robot is left-aligned with one of the bases
//(above the leftmost wall of the base).
//After these methods are called, both of these conditions must remain true.
//pre: on a base
//post: on next base to right
// (if was on rightmost base, now on leftmost base)
public static void nextBase()
{
turnAround();
while (!Robot.frontIsClear())
{
Robot.turnLeft();
Robot.move();
turnRight();
}
Robot.turnLeft();
if ( Robot.frontIsClear() )
{
Robot.move();
Robot.turnLeft();
}
else
{
turnAround();
moveToWall();
backUp();
turnRight();
}
}
//pre: on a non-empty base
//post: in same place; top has been removed
public static void removeTop()
{
//gotoTop
//remove
//comeback
int w=width();
if(w>=1){
Lab4.moveToLight();
Lab4.backUp();
}
Lab4.turnRight();
while(Robot.onDark()){
Robot.makeLight();
Robot.move();
}
Lab4.robotReturnStartPoint();
}
//pre: on a base
//post: in same place; returns number of non-empty bases
public static int numTowers()
{
int base = 0;
if(Robot.onDark()){
base++;
}
Lab4.nextBase();
if(Robot.onDark()){
base++;
}
nextBase();
if(Robot.onDark()){
base++;
}
nextBase();
return base;
}
//pre: in leftmost square of a disk
//post: in same place; returns width of disk
public static int width()
{
int width=0;
Lab4.turnRight();
while(Robot.onDark()){
Robot.move();
width=width+1;
}
Lab4.turnAround();
while(Lab4.robotFrontIsDark()){
Robot.move();
}
Lab4.turnRight();
return width;
}
//pre: on a base
//post: in same place; returns width of top disk, or 1000 if base is empty
public static int topWidth()
{
while(Lab4.robotFrontIsDark()){
Robot.move();
}
int t=0;
t=Lab4.width();
if(t==0){
t=1000;
}
Lab4.robotReturn();
return t;
}
//pre: on a base
//post: on base with smallest disk
public static void findSmallest()
{
while(Lab4.topWidth()!=1){
Lab4.nextBase();
}
}
//pre: on a base
//post: smallest disk has been moved to top of next base; robot on that base
public static void moveSmallest()
{
Lab4.findSmallest();
while(Lab4.robotFrontIsDark()){
Robot.move();
}
Robot.makeLight();
Lab4.robotReturn();
Lab4.nextBase();
while(Robot.onDark()){
Robot.move();
}
Robot.makeDark();
Lab4.robotReturn();
}
//pre: on a non-empty base
//post: top disk has been moved to top of next base (to right);
// robot on that base
public static void moveTopToNextBase()
{
int tw=Lab4.topWidth();
Lab4.removeTop();
Lab4.nextBase();
Lab4.moveToLight();
Lab4.turnRight();
while(tw>0){
Robot.makeDark();
Robot.move();
tw=tw-1;
}
Lab4.robotReturnStartPoint();
}
//pre: on a non-empty base
//post: top disk has been moved to top of previous base (to left);
// robot on that base
public static void moveTopToPreviousBase()
{
int tw=Lab4.topWidth();
Lab4.removeTop();
Lab4.nextBase();
Lab4.nextBase();
Lab4.moveToLight();
Lab4.turnRight();
while(tw>0){
Robot.makeDark();
Robot.move();
tw=tw-1;
}
Lab4.robotReturnStartPoint();
}
//pre: on a base
//post: top disk has been moved on top of larger disk on another base;
// robot on that base
public static void moveNonSmallest()
{
int tw1 = topWidth();
Lab4.nextBase();
int tw2 = topWidth();
Lab4.nextBase();
int tw3 = topWidth();
Lab4.nextBase();
if(tw1>tw2&&tw3==1000){
Lab4.moveTopToPreviousBase();
}
if (tw1>tw2&&tw1>tw3){
if(tw2==1){
Lab4.nextBase();
Lab4.nextBase();
Lab4.moveTopToNextBase();
}
else{
Lab4.nextBase();
Lab4.moveTopToPreviousBase();
}
}
if(tw2>tw3&&tw2>tw1){
if(tw3==1){
Lab4.moveTopToNextBase();
}
else{
Lab4.nextBase();
Lab4.nextBase();
Lab4.moveTopToPreviousBase();
}
}
if(tw3>tw2&&tw3>tw1){
if(tw2==1){
Lab4.moveTopToPreviousBase();
}
else{
Lab4.nextBase();
Lab4.moveTopToNextBase();
}
}
}
//pre: on a base; there is only one tower
//post: tower has moved to a different base; robot on some base
public static void moveTower()
{
int numTowers=numTowers();
numTowers=2;
while(numTowers>1){
Lab4.moveSmallest();
Lab4.moveNonSmallest();
numTowers=numTowers();
}
}
}
