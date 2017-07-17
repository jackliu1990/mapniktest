import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import mapnik.*;

public class mapnikWMTS {
	public static void main(String[] args) throws IOException {
	    Mapnik.initialize();
	    Double minX = 9.9995836;
		Double minY = 54.9995839;
		Double maxX = 15.0004170;
		Double maxY = 60.0004173;
	    String output = "/home/liufeng/workspace/testmapnik/output/";
	    
	    int zoom =4;
	    for(int i=0;i<=zoom;i++){
	    	int total_num = (int)Math.pow(4,i);//
	    	int col_num = (int)Math.pow(2, i);
	    	
	    	int pixelExtent = 256 * col_num;
	    	
	    	for(int k=0;k<col_num;k++){
	    		for(int l=0;l<col_num;l++){
	    			
	    			MapDefinition m = new MapDefinition();
	    			m.loadMap("/home/liufeng/workspace/testmapnik/xml/mapnikWMTS.xml", false);
	    			m.setSrs(Projection.LATLNG_PARAMS);
	    			m.resize(256, 256);
	    			    
	    			    
	    			double dX=maxX-minX;
	    			double dY=maxY-minY;
	    			
	    			double dpX = dX/col_num;
	    			double dpY = dY/col_num;
	    			
	    			double renderXmin = minX+k*dpX;
	    			double renderYmin = maxY-l*dpY;
	    			double renderXmax = renderXmin + 1*dpX;
	    			double renderYmax = renderYmin-1*dpY;
	    			Box2d bounds = new Box2d(renderXmin,renderYmin,renderXmax,renderYmax);
	    			m.zoomToBox(bounds);

	    		    Image image = new Image(256,256);
	    		    Renderer.renderAgg(m,image);
	    		    byte[] contents = image.saveToMemory("png");
	    		    image.dispose();
	    		    m.dispose();
	    		    File fosDir = new File(output+i);
	    		    if(!fosDir.exists()){
	    		    	fosDir.mkdir();
	    		    }
	    		    FileOutputStream fos =new FileOutputStream(output+i+"/"+k+"_"+l+".png");
	    		    fos.write(contents);
	    		    fos.flush();
	    		    fos.close();
	    		}
	    	}
	    }
	}
}
