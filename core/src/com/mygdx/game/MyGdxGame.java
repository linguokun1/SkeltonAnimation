package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonMeshRenderer;

public class MyGdxGame extends ApplicationAdapter {
//	SpriteBatch batch;
//	Texture img;

	private SkeletonMeshRenderer render;
	private SkeletonData sData;
	private AnimationState state;
	private Skeleton skeleton;
	private PolygonSpriteBatch polygonBatch;

	@Override
	public void create () {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");

		render = new SkeletonMeshRenderer();
		// 获取纹理集合
		TextureAtlas tAtlas = new TextureAtlas(Gdx.files.internal("dragon.atlas"));
		// 读取json信息
		SkeletonJson sJson = new SkeletonJson(tAtlas);
		sJson.setScale(1f);// 缩放，以后不可更改

		sData = sJson.readSkeletonData(Gdx.files.internal("dragon.json"));
		// 初始化动画信息
		AnimationStateData animData = new AnimationStateData(sData);
		state = new AnimationState(animData);
		// 初始化骨骼信息
		skeleton = new Skeleton(sData);
		// 初始化batch
		polygonBatch = new PolygonSpriteBatch();
		// 播放动画
		state.setAnimation(0, "flying", true);
		// 设置位置
		skeleton.setPosition(900, 300);
	}

	@Override
	public void render () {
//		handleInput();
		Gdx.gl.glClearColor(255, 255, 255, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();

		// 清屏
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// 动画控制器更新时间步
		state.update(Gdx.graphics.getDeltaTime());
		// 动画控制器控制骨骼动画
		state.apply(skeleton);
		// 骨骼逐级进行矩阵变换
		skeleton.updateWorldTransform();
		// 绘制
		polygonBatch.begin();
		render.draw(polygonBatch, skeleton);
		polygonBatch.end();
	}
	
	@Override
	public void dispose () {
//		batch.dispose();
//		img.dispose();
	}

	/**处理按键输入事件*/
	public void handleInput() {
		// TODO Auto-generated method stub
		if (Gdx.input.isKeyJustPressed(Input.Keys.Z)
				|| (Gdx.input.justTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() / 2 && Gdx.input.getY() < Gdx.graphics.getHeight() / 2)) {
			state.setAnimation(0, "Jump", true);
		}else if(Gdx.input.isKeyJustPressed(Input.Keys.B) || (Gdx.input.justTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() / 2 && Gdx.input.getY() > Gdx.graphics.getHeight() / 2)){
			state.setAnimation(0, "Crouch", true);
		}else if(Gdx.input.isKeyJustPressed(Input.Keys.B) || (Gdx.input.justTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 2)){
			state.setAnimation(0, "Attack", true);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.A)
				|| (Gdx.input.justTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 2)) {
			System.out.println("Switch");
		}
	}
}
