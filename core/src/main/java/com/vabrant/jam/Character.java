package com.vabrant.jam;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.vabrant.jam.Constants.DeployableType;
import com.vabrant.jam.Constants.Direction;
import com.vabrant.jam.Constants.Lane;
import com.vabrant.jam.Constants.PlayerType;

import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Character extends Deployable implements Poolable {

	private Direction moveDirection;
	private final float moveSpeed;
	
	private float characterYOffset;
	private boolean isMovable;
	private boolean isDead;
	private int health;
	private final int maxHealth;
	private final float shootInterval;
	private float timer;
	private Character target;
	private Player owningPlayer;
	private TextureRegion oppositeRegion;
	private Rectangle rangeBounds;
	public final DeployableType type;
	private RemoveCharacterCallback removeCharacterCallback;
	private Lane lane;
	
	public Character(DeployableType type, TextureRegion entityRegion, int damage, float shootInterval, float moveSpeed, int maxHealth, float range) {
		super(entityRegion, damage);
		this.type = type;
		oppositeRegion = new TextureRegion(entityRegion);
		oppositeRegion.flip(true, false);
		this.shootInterval = shootInterval;
		this.moveSpeed = moveSpeed;
		this.maxHealth = maxHealth;
		health = maxHealth;
		
		float width = entityRegion.getRegionWidth() / 8;
		float height = entityRegion.getRegionHeight() / 8;
		boundingBox.width = width * 3;
		boundingBox.height = height * 3;
		
		rangeBounds = new Rectangle(0, 0, getWidth() + (range * 2), getHeight());
	}
	
	protected abstract boolean canEngage(Character c);
	
	public void setMovable(boolean movable) {
		isMovable = movable;
	}
	
	public void setYOffset(float yOffset) {
		this.characterYOffset = yOffset;
	}

	public void translateX(float x) {
		boundingBox.x += x;
		float centerX = (rangeBounds.width - getWidth()) / 2;
		rangeBounds.x = boundingBox.x - centerX;
	}

	public void setup(Player player) {
		owningPlayer = player;
	}
	
	public void setup(Player player, RemoveCharacterCallback callback, Lane lane) {
		owningPlayer = player;
		removeCharacterCallback = callback;
		this.lane = lane;
		
//		PlayerType type = owningPlayer.type.equals(PlayerType.HUMAN) ? PlayerType.AI : PlayerType.HUMAN;
		
		switch(player.type) {
			case HUMAN:
				this.moveDirection = Direction.RIGHT;
				break;
			case AI:
				this.moveDirection = Direction.LEFT;
				boundingBox.x = Constants.PLATFORM_WIDTH - getWidth();
				break;
		}
	}
	
	public void removeFromPlatform() {
		if(removeCharacterCallback != null) removeCharacterCallback.removeCharacter(this);
	}
	
	public Lane getLane() {
		return lane;
	}

	public void setTarget(Character target) {
		this.target = target;
	}
	
	public boolean isDead() {
		return health == 0;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	@Override
	public float getY() {
		return super.getY() + characterYOffset;
	}
	
	public void hit(int damage) {
		health = MathUtils.clamp(health -= damage, 0, maxHealth);
		if(health == 0) isDead = true;
	}
	
	private void shoot(float delta) {
		if(target == null) return;
		
		timer += delta;
		
		if(timer >= shootInterval) {
			timer = 0;
			target.hit(damage);
			if(target.isDead()) target = null;
		}
	}
	
	private void move(float delta) {
		if(!isMovable || target != null) return;
		float amount = moveSpeed * delta;
		translateX(moveDirection.equals(Direction.RIGHT) ? amount : -amount);
	}
	
	private void checkIfTargetIsInRange() {
		if(target == null) return;
		if(!isInRange(target.boundingBox)) {
			target = null;
			timer = 0;
		}
	}
	
	private boolean isInRange(Rectangle b) {
		return overlaps(b);
	}
	
	private boolean overlaps(Rectangle b) {
		return rangeBounds.x < b.x + b.width && rangeBounds.x + rangeBounds.width > b.x;
	}
	
	private void findTarget() {
		if(target != null) return;
		
		int smallestDistanceIndex = -1;
		Array<Character> characters = owningPlayer.oppositePlayer.characters;
		
		for(int i = 0; i < characters.size; i++) {
			Character c = characters.get(i);
			
			if(c.isDead()) continue;
			
			if(isInRange(c.boundingBox)) {
				if(!canEngage(c)) continue;
				smallestDistanceIndex = i;
				break;
			}
		}
		
		if(smallestDistanceIndex > -1) {
			target = characters.get(smallestDistanceIndex);
		}
	}
	
	@Override
	public void update(float delta) {
		if(isDead) return;
		checkIfTargetIsInRange();
		findTarget();
		move(delta);
		shoot(delta);
	}
	
	private TextureRegion getDrawRegion() {
		if(!isMovable) {
			return entityRegion;
		}
		else {
			return moveDirection.equals(Direction.RIGHT) ? entityRegion : oppositeRegion;
		}
	}
	
	@Override
	public void draw(Batch batch, ShapeDrawer shapeDrawer, float xOffset, float yOffset, float scale) {
		float width = getWidth() * scale;
		float height = getHeight() * scale;
		
		TextureRegion region = getDrawRegion();
		
		batch.draw(region, (boundingBox.x * scale) + xOffset, boundingBox.y + yOffset + (characterYOffset * scale), width, height);
		debug(shapeDrawer, xOffset, yOffset + (characterYOffset * scale), scale);
		entityRegion.flip(false, false);
	}
	
	private void debug(ShapeDrawer shapeDrawer, float offsetX, float offsetY, float scale) {
		if(!Constants.DEBUG) return;
		debugBoundingBox(shapeDrawer, offsetX, offsetY, scale);
		debugRangeBounds(shapeDrawer, offsetX, offsetY, scale);
	}
	
	private void debugRangeBounds(ShapeDrawer shapeDrawer, float offsetX, float offsetY, float scale) {
		shapeDrawer.rectangle((rangeBounds.x * scale) + offsetX, rangeBounds.y + offsetY, rangeBounds.width * scale, rangeBounds.height * scale);
	}

	@Override
	public void reset() {
		isDead = false;
		rangeBounds.x = 0;
		rangeBounds.y = 0;
		boundingBox.x = 0;
		boundingBox.y = 0;
		owningPlayer = null;
		target = null;
		timer = 0;
		health = maxHealth;
		removeCharacterCallback = null;
	}

}
