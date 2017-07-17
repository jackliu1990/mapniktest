import java.io.FileOutputStream;
import java.io.IOException;

import mapnik.*;

public class mapnikrender {
	public static void main(String[] args) throws IOException {
		Mapnik.initialize();
		//-180,-85.05112877980659,180,85.05112877980659
		Double minX = 9.9995836;
		Double minY = 54.9995839;
		Double maxX = 15.0004170;
		Double maxY = 60.0004173;
		String output = "/home/liufeng/workspace/testmapnik/output/";
		MapDefinition m = new MapDefinition();
		m.loadMap("/home/liufeng/workspace/testmapnik/xml/mapnikrender.xml", false);
		
		Layer layer = new Layer("merg1_color_relief_google");
		layer.setSrs("+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0.0 +k=1.0 +units=m +nadgrids=@null +wktext +no_defs +over");
		String[] styles = new String []{"merg1_color_relief_google"};
		layer.setStyles(styles);
		Parameters params=new Parameters();
		params.put("type", "gdal");
		params.put("file", "/home/liufeng/workspace/testmapnik/data/merg1_color_relief_google.tif");
		
		Datasource ds=DatasourceCache.create(params);
		layer.setDatasource(ds);
		m.addLayer(layer);
		
		m.setSrs(Projection.LATLNG_PARAMS);
		m.resize(256, 256);
		Box2d bounds = new Box2d(minX, minY, maxX, maxY);
		m.zoomToBox(bounds);
		Image image = new Image(256, 256);
		Renderer.renderAgg(m, image);
		byte[] contents = image.saveToMemory("png");
		image.dispose();
		m.dispose();
		FileOutputStream fos = new FileOutputStream(output + "testmapnik.png");
		fos.write(contents);
		fos.flush();
		fos.close();
	}
}
