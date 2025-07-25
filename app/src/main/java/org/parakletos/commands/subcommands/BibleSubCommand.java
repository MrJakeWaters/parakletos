package org.parakletos;

// java 
import java.io.File;
import java.util.Arrays;
import java.io.IOException;
// avro
import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericDatumReader;

public class BibleSubCommand {
	public BibleSuperSearchApi functions;
	public BibleSubCommand () {
		this.functions = new BibleSuperSearchApi();	

	}
	public void showRandomBibleMessage() {
		String verseReference = this.functions.getRandomBibleMessage();
		String content = this.functions.getBibleVerse(verseReference.replace(" ", "%20"));
		System.out.println(content);

	}
	public void showRandomBibleVerse() {
		String verseReference = this.functions.getRandomBibleVerseReference();
		String content = this.functions.getBibleVerse(verseReference.replace(" ", "%20"));
		System.out.println(content);

	}
	public void showRandomBibleChapter() {
		String verseReference = this.functions.getRandomBibleChapterReference();
		String content = this.functions.getBibleVerse(verseReference.replace(" ", "%20"));
		System.out.println(content);

	}
	public void showBibleChapter(String book, int chapter) {
		String verseReference = this.functions.getBibleBookChapterReference(book, chapter);
		String content = this.functions.getBibleVerse(verseReference.replace(" ", "%20"));
		System.out.println(content);

	}
	public void getDailyBibleChapter() {
		// check if a daily bible chapter exists
		DailyBibleChapter daily = new DailyBibleChapter();
		String dailyChapterFile = String.format("%sdaily-bible-chapter/%s.avro", Init.PK_CONFIG_HOME, daily.getRefreshDate());

		// check if file exists
		File f = new File(dailyChapterFile);
		if (f.exists()) {
			// the existing file
			Schema schema = ReflectData.get().getSchema(DailyBibleChapter.class);
			DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
			try {
				DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File(f.getAbsolutePath()), reader);
				GenericRecord dailyChapter = null;
				while (dataFileReader.hasNext()) {
					DailyBibleChapter output = new DailyBibleChapter(dataFileReader.next(dailyChapter));
					System.out.println(output.getText());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else  {
			// get chapter content
			String reference = this.functions.getRandomBibleChapterReference();
			String content = this.functions.getBibleVerse(reference.replace(" ", "%20"));
			String[] referenceArr = reference.split(" ");
			
			// parse reference to chapter/book
			String book = String.join(" ", Arrays.copyOfRange(referenceArr, 0, referenceArr.length-1));
			int chapter = Integer.parseInt(referenceArr[referenceArr.length-1].split(":")[0]);

			// set attributes
			daily.setText(content);
			daily.setChapter(chapter);
			daily.setBook(book);
		
			// write to disk
			Avro avro = new Avro();
			avro.write(dailyChapterFile, daily);
			System.out.println(content);

		}
	}  
}
