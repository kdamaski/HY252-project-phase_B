package project.model;


/**
 * 
 * @author csd3755 K.D
 * @category Model
 */
abstract public class  Tile {
	String image;
	/**
	 * @brief sets the image of the Tile 
	 * @param image
	 */
	public Tile(String image) {
		this.image = image;
	}
	/**
	 * 
	 * @return String imageID
	 */
	public abstract String getId();
}
/**
 * 
 * @brief mosaic specification
 * @category mosaics
 * @implNote Id 0 stands for green, 1 for red, 2 for yellow
 */
class mosaic extends Tile{
	mosaic(String image){
		super(image);
	}
	public String getId() {
		return this.image;
	}
}
/**
 * 
 * @brief Statue specification
 * @category Statues
 */
abstract class statue extends Tile{
	statue(String image){
		super(image);
	}

}
class sphinx extends statue {
	sphinx(String image){
		super(image);
	}
	public String getId() {
		return this.image;
	}
}
class caryatid extends statue {
	caryatid(String image){
		super(image);
	}
	public String getId() {
		return this.image;
	}
}
//Encd of statue area

/**
 * @brief amphora specification
 * @implNote 0 stands for blue, 1 brown, 2 green, 3 purple, 4 red, 5 yellow
 *
 */
class amphora extends Tile {
	amphora(String image){
		super(image);
	}
	public String getId() {
		return this.image;
	}
}

/**
 * @brief skeleton specification
 * @category skeleton
 * @implNote {0,1,2,3}->{BigTop,BigBot,SmallTop,SmallBot}
 */
class skeleton extends Tile{	
	skeleton(String image){
		super(image);
	}
	public String getId() {
		return this.image;
	}
}

/**
 * @brief landSlide specification
 * @category landSlide
 *
 */
class landSlide extends Tile {
	landSlide(String image){
		super(image);
	}
	public String getId() {
		return this.image;
	}
}