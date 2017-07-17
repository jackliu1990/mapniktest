import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import mapnik.*;

public class Main {
	public static void main(String[] args) throws IOException {
	    Mapnik.initialize();
	    //-8024477.28459,5445190.38849,-7381388.20071,5662941.44855
	    Double minX= -8024477.28459;
	    Double minY = 5445190.38849;
	    Double maxX = -7381388.20071;
	    Double maxY = 5662941.44855;
	    String output = "/home/liufeng/workspace/testmapnik/output/";
	    MapDefinition m = new MapDefinition();
	    m.loadMap("/home/liufeng/workspace/testmapnik/xml/map.xml", false);
	    m.setSrs(Projection.SRS900913_PARAMS);
	    m.resize(256, 256);
	    Box2d bounds = new Box2d(minX,minY,maxX,maxY);
	    m.zoomToBox(bounds);
	    Image image = new Image(256,256);
	    Renderer.renderAgg(m,image);
	    byte[] contents = image.saveToMemory("png");
	    image.dispose();
	    m.dispose();
	    FileOutputStream fos =new FileOutputStream(output+"testmapnik.png");
	    fos.write(contents);
	    fos.flush();
	    fos.close();
	}
}