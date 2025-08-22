package org.parakletos;

// java
import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.reflect.Field;
// avro
import org.apache.avro.Schema;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericDatumReader;

public class Avro<T> {
	public Avro() {}
	public void createWritePath(String filename) {
		// ensure filename is clean with no extra "/"
		filename = filename.replaceAll("//","/");

		// function creates folders if they don't already exists when trying to write file
		String fullPath = "";
		for (String directory: Arrays.copyOfRange(filename.split("/"), 1, filename.split("/").length)) { // only loop through index [1:] (first index is '/')
			File f = new File(fullPath);
			if (!f.exists() && !fullPath.equals("")) {
				f.mkdir();
				System.out.println(String.format("[INFO] Directory Created: %s", fullPath));

			}
			fullPath = String.format("%s/%s", fullPath, directory);
		}
	}
	public List<GenericRecord> getRecords(String filename, Class classType) {
			Schema schema = ReflectData.get().getSchema(classType);
			DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
			List<GenericRecord> output = new ArrayList<>();

			// try to read the file
			try {
				DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File(filename), reader);
				GenericRecord record = null;
				while (dataFileReader.hasNext()) {
					record = dataFileReader.next(record);
					output.add(record);

				}
				return output;

			} catch (IOException e) {
				e.printStackTrace();
				return output;

			}
	}
	public boolean write(String filename, T data) {
		// ensure path exists to write file
		this.createWritePath(filename);

		// set schema of dynamic data object
        Schema schema = ReflectData.get().getSchema(data.getClass());

		// convert java class to generic record for SpecificDatumWriter
		Map<String, Object> dataMap = new AvroAbstract(data).getMap();
		GenericRecord avroRecord = new GenericData.Record(schema);
		schema.getFields().forEach(field -> {
			avroRecord.put(field.name(), dataMap.get(field.name()));
		});

		// create avro writer
		DatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
		DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(writer);

		// set data record schema to writer
		try {
			dataFileWriter.create(schema, new File(filename));

			// add object to writer and write
			dataFileWriter.append(avroRecord);
			dataFileWriter.close();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;

		}
	}
}
