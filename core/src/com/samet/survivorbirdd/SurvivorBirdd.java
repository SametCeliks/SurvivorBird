package com.samet.survivorbirdd;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBirdd extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture rocket;
	float rocketX=0;
	float rocketY=0;
	int gameState=0;
	float velocity=0;
	float gravity=0.9f;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	float enemyVelocity = 8;
	Random random;

	Circle rocketCircle;
	int score=0;
	int scoredEnemy=0;
	BitmapFont font;
	BitmapFont font2;

	ShapeRenderer shapeRenderer;





	int enemySet = 5;
	float [] enemyX = new float[enemySet];
	float[] enemyOffSet1 = new float[enemySet];
	float[] enemyOffSet2 = new float[enemySet];
	float[] enemyOffSet3 = new float[enemySet];
	float distance = 0;
	Circle[] enemyCircles1;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;




	
	@Override
	public void create () {
		//oyun acildiginda olacak seyler
		batch = new SpriteBatch();
		background = new Texture("background.png");
		rocket = new Texture("rocket.png");
		enemy1 = new Texture("enemy.png");
		enemy2 = new Texture("enemy.png");
		enemy3 = new Texture("enemy.png");

		distance = Gdx.graphics.getWidth()/2;
		random = new Random();


		//rocketin konumu
		rocketX=Gdx.graphics.getWidth()/2-rocket.getHeight()/1;
		rocketY=Gdx.graphics.getHeight()/3;

		//shapeRenderer = new ShapeRenderer();

		rocketCircle = new Circle();
		enemyCircles1 = new Circle[enemySet];
		enemyCircles2 = new Circle[enemySet];
		enemyCircles3 = new Circle[enemySet];

		font=new BitmapFont();
		font.setColor(Color.GREEN);
		font.getData().setScale(4);

		font2=new BitmapFont();
		font2.setColor(Color.GREEN);
		font2.getData().setScale(6);


		for (int i=0; i<enemySet; i++ ){

			enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i]=Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i * distance;

			enemyCircles1[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
		}



	}

	@Override
	public void render () {
		//oyun devam ettikce devamlı dönecek seyler
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


		 if (gameState==1){

			 if (enemyX[scoredEnemy]<Gdx.graphics.getWidth()/2-rocket.getHeight()/1){
				 score++;

				 if (scoredEnemy < enemySet - 1){
					 scoredEnemy++;
				 }else{
					 scoredEnemy=0;
				 }

			 }


			 //oyun baslatildiysa
			 if (Gdx.input.justTouched()){
				 velocity = -15;
			 }
				//düsmanlarin sürekli olarak ekrana gelmesi
			 for (int i=0; i<enemySet; i++){

				 if (enemyX[i] < -Gdx.graphics.getWidth()/15){
					 enemyX[i]=enemyX[i] + enemySet * distance;

					 enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					 enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					 enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

				 }else {
					 enemyX[i] = enemyX[i] - enemyVelocity;
				 }

				 batch.draw(enemy1,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffSet1[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				 batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffSet2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				 batch.draw(enemy3,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffSet3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

				 enemyCircles1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2+enemyOffSet1[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				 enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2+enemyOffSet2[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				 enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2+enemyOffSet3[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);



			 }



			 if (rocketY>0 ){
				 velocity=velocity+gravity;
				 rocketY=rocketY-velocity;
			 }else {
				 gameState=2;
			 }



		 }else if (gameState ==0){
			 if (Gdx.input.justTouched()){
				 gameState=1;
			 }
			 //Oyun Bitince
		 } else if (gameState == 2) {

			 font2.draw(batch,"Game Over! Tap To Try Again!",300,Gdx.graphics.getHeight()/2);
			 if (Gdx.input.justTouched()){
				 gameState=1;
				 rocketY=Gdx.graphics.getHeight()/3;

				 for (int i=0; i<enemySet; i++ ){

					 enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					 enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					 enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					 enemyX[i]=Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i * distance;

					 enemyCircles1[i] = new Circle();
					 enemyCircles2[i] = new Circle();
					 enemyCircles3[i] = new Circle();
				 }
				 velocity=0;
				 scoredEnemy=0;
				 score=0;


			 }
		 }


		batch.draw(rocket,rocketX,rocketY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

		 font.draw(batch,String.valueOf(score),100,200);

		batch.end();

		rocketCircle.set(rocketX +Gdx.graphics.getWidth()/30,rocketY+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.GREEN);
		//shapeRenderer.circle(rocketCircle.x,rocketCircle.y,rocketCircle.radius);

		for (int i =0; i<enemySet; i++){
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2+enemyOffSet1[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2+enemyOffSet2[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2+enemyOffSet3[i] + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

			if (Intersector.overlaps(rocketCircle,enemyCircles1[i]) || Intersector.overlaps(rocketCircle,enemyCircles2[i]) || Intersector.overlaps(rocketCircle,enemyCircles3[i])){
				gameState = 2;
			}

		}
		//shapeRenderer.end();



	}
	
	@Override
	public void dispose () {

	}
}
