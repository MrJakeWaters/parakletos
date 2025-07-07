package org.prayer;

import java.io.File;
import java.time.Instant;
import java.io.IOException;
import org.apache.orc.Writer;
import org.apache.orc.OrcFile;
import java.lang.reflect.Field;
import org.apache.hadoop.fs.Path;
import org.apache.orc.TypeDescription;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;

public class OrcWriter<T> {
	private T object;
	public String name;
	public TypeDescription schema;
	private Configuration conf;
	private String filePartitionDate;

	// constructor
	public OrcWriter(T object) {
		this.object = object;
		this.name = object.getClass().getSimpleName();
		this.setSchema();
		this.conf = new Configuration();
	}

	// set writer
	public void setSchema() {
        this.schema = TypeDescription.createStruct();
		try {
			for (Field field: this.object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				String key = field.getName();
				String value = String.valueOf(field.get(this.object));

				// set values inside of struct
				this.schema.addField(key, TypeDescription.createString());
				this.schema.setAttribute(key, value);
			}	
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void createWriteDir(String dirPath) {
		// create configuration directory if it doesn't exist
		File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdir();
		}
	}

	// getter for schema
	public TypeDescription getSchema() {
		return this.schema;
	}
	
	public void writeFile(String filePath, Instant objectTimestamp) {
		try {
			// make sure directory exists and create it if not
			this.createWriteDir(filePath);
			System.out.println(filePath);

			// create writer
			Path path = new Path(filePath);
			Writer writer = OrcFile.createWriter(path, OrcFile.writerOptions(this.conf).setSchema(this.schema));

			// Create a row batch
			// batch is required to write an orc file
			VectorizedRowBatch batch = this.schema.createRowBatch();
			writer.addRowBatch(batch);
			
			// close everything out
			batch.reset();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
