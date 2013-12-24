package zap.turrem.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class JarLoader
{
	protected static String[] getClassList(File file) throws IOException
	{
		List<String> classNames = new ArrayList<String>();
		ZipInputStream zip = new ZipInputStream(new FileInputStream(file.getAbsolutePath()));
		for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry())
		{
			if (entry.getName().endsWith(".class") && !entry.isDirectory())
			{
				StringBuilder className = new StringBuilder();
				for (String part : entry.getName().split("/"))
				{
					if (className.length() != 0)
					{
						className.append(".");
					}
					className.append(part);
					if (part.endsWith(".class"))
					{
						className.setLength(className.length() - ".class".length());
					}
				}
				classNames.add(className.toString());
			}
		}
		zip.close();
		return classNames.toArray(new String[0]);
	}
}