package id.com.templates.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FunctionUtils {
	public static String getNumericOnly(String text) {
		String tmp = "";
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) >= '0' && text.charAt(i) <= '9') {
				tmp += text.charAt(i);
			}
		}
		return tmp;
	}
	
	public static String moneyToText(BigDecimal money) {
		if (money == null) {
			return "0.00";
		}
		
		if (money != null && money.compareTo(new BigDecimal(0)) != 0) {
			boolean isNegative = false;
			if(money.toString().contains("-")){
				money = money.abs();
				isNegative = true;
			}
			
			String lsMoney1 = "";
			String lsMoney2 = "";

			if (!money.toString().contains(".")) {
				lsMoney1 += "00.";
				for (int i = money.toString().length() - 1; i > -1; i--) {
					lsMoney1 += money.toString().charAt(i);
				}
			} else {
				boolean point = false;
				money.toString().indexOf(".");
				for (int i = money.toString().indexOf(".") + 2; i >= money.toString().indexOf("."); i--) {
					lsMoney1 += money.toString().charAt(i);
				}

				for (int i = money.toString().length() - 1; i > -1; i--) {
					if (point) {
						lsMoney1 += money.toString().charAt(i);
					}
					if (money.toString().charAt(i) == '.') {
						point = true;
					}
				}
			}

			for (int i = 0; i < lsMoney1.length(); i++) {
				if (i == 6 || i == 9 || i == 12 || i == 15 || i == 18)
					lsMoney2 += ",";
				lsMoney2 += lsMoney1.charAt(i);
			}

			lsMoney1 = "";

			for (int i = lsMoney2.length() - 1; i > -1; i--) {
				lsMoney1 += lsMoney2.toString().charAt(i);
			}

			if(isNegative){
				lsMoney1 = "-"+lsMoney1;
			}
			
			return (lsMoney1);
		} else {
			return ("0.00");
		}
	}

	public static BigDecimal moneyToBigDecimal(String money) {
		if (!money.equals("")) {
			DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance(Locale.getDefault());
			try {
				String value = format.parse(money).toString();
				String a = String.valueOf(value);
				BigDecimal dec = new BigDecimal(a);
				return dec;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} 
		return new BigDecimal("0");
	}
	
	public static Double moneyToDouble(String money) {
		if (!money.equals("")) {
			String temp = "";
			char c;

			for (int i = 0; i < money.length(); i++) {
				c = money.charAt(i);
				if (c >= '0' && c <= '9' || c == '.')
					temp += c;
			}
			return new Double(temp);
		} else {
			return new Double("0");
		}
	}
	
	public static String toTitleCase(String s) {
		String lowerCase = s.toLowerCase();
		String[] split = lowerCase.split(" ");
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < split.length; i++) {
			char charAt = split[i].charAt(0);
			String upper = String.valueOf(charAt).toUpperCase();
			String substring = upper+split[i].substring(1);
			sb.append(substring+" ");
		}
		
		return sb.toString();
	}
	
	public static String formatNumber(Integer value, String masking, Integer resultLength) {
		if(value != null){
			String result = "";
			
			if (masking == null || masking.equals("")) {
				masking = "0";
			}
			
			for (int i = 0; i < resultLength; i++) {
				result = result + masking;
			}
			
			result = result + value.toString();
			
			return result.substring(result.length() - resultLength, result.length());
		}
		return "";
	}
	
	
	public static String formatNumber(String value, String masking, Integer resultLength) {
		if(value != null){
			String result = "";
			if (masking == null || masking.equals("")) {
				masking = "0";
			}
			for (int i = 0; i < resultLength; i++) {
				result = result + masking;
			}
			result = result + value;
			return result.substring(result.length() - resultLength, result.length());
		}
		return "";
	}
	
	public static String formatNumber(String value, String masking, Integer resultLength, String firstSequenceNo) {
		if(value != null){
			String result = "";
			
			if (masking == null || masking.equals("")) {
				masking = "0";
			}
			
			for (int i = 0; i < resultLength; i++) {
				result = result + masking;
			}
			
			result = value + result;
			
			return result.substring(0, resultLength - 1) + firstSequenceNo;
		}
		return "";
	}
	
	public static String getMoneyIndonesia(BigDecimal money) {
		NumberFormat nf = new DecimalFormat("#,###.##");
		String str = moneyToText(money);
		return nf.format(Long.parseLong(str.trim().replaceAll("\\..+", "").replaceAll(",", ""))).replaceAll("\\..+", "").replaceAll(",", "\\.");
	}
	
	public static String formatNumberLeftAlign(String value, String masking, Integer resultLength) {
		String result = "";
		if (masking == null || masking.equals("")) {
			masking = "0";
		}
		for (int i = 0; i < resultLength; i++) {
			result = masking + result;
		}
		result = value + result;
		return result.substring(0, resultLength);
	}
	
	public static String terbilang(double number) {
		String bilangan[] = new String[] { "", "satu ", "dua ", "tiga ", "empat ", "lima ", "enam ", "tujuh ", "delapan ", "sembilan ", "sepuluh ",
		"sebelas " };

		StringBuffer sb = new StringBuffer();
		
		if (number < Double.valueOf(12)) {
			sb.append(bilangan[(int) number]);
		}
		
		if (number >= 12 && number < 20) {
			sb.append(terbilang(number - 10));
			sb.append("belas ");
		}
		
		if (number >= 20 && number < 100) {
			sb.append(terbilang(number / 10));
			sb.append("puluh ");
			sb.append(terbilang(number % 10));
		}
		
		if (number >= 100 && number < 200) {
			sb.append("seratus ");
			sb.append(terbilang(number % 100));
		}
		
		if (number >= 200 && number < 1000) {
			sb.append(terbilang(number / 100));
			sb.append("ratus ");
			sb.append(terbilang(number % 100));
		}
		
		if (number >= 1000 && number < 2000) {
			sb.append("seribu ");
			sb.append(terbilang(number % 1000));
		}
		
		if (number >= 2000 && number < 1000000) {
			sb.append(terbilang(number / 1000));
			sb.append("ribu ");
			sb.append(terbilang(number % 1000));
		}
		
		if (number >= 1000000 && number < 1000000000) {
			sb.append(terbilang(number / 1000000));
			sb.append("juta ");
			sb.append(terbilang(number % 1000000));
		}
		
		if (number >= 1000000000 && number < 1000000000000L) {
			sb.append(terbilang(number / 1000000000));
			sb.append("milyar ");
			sb.append(terbilang(number % 1000000000));
		}
		
		return sb.toString();
	}
	
	public static List<String> getDataFile(BufferedReader br) {
		String record = null;
		try {
			record = new String();
			ArrayList<String> iALWords = new ArrayList<String>();
			while ((record = br.readLine()) != null) {
				System.out.println(record);
				iALWords.add(record);
			}
			
			return iALWords;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ArrayList<String>();
	}
	
	public static boolean contraintPassword(String password){
		return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$");
	}
	
	public static byte[] compress (Object o){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte data[] = null;
		try {
			GZIPOutputStream gZip = new GZIPOutputStream(bos);
			ObjectOutputStream oos = new ObjectOutputStream(gZip);
			oos.writeObject(o);
			oos.flush(); 
			oos.close(); 
			bos.close();
			data = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		return data;
	}
	
	public static <T> Object unCompress (byte[] data){
		if (data==null) return null;
		
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		Object object = null;
		try {
			GZIPInputStream giZip = new GZIPInputStream(bis);
			ObjectInputStream ois = new ObjectInputStream(giZip);
			object = ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return object;
	}
}
