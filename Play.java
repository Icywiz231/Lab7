package p1;
import java.io.*;
import java.util.Scanner;

public class Play {
	static int length = 0;
	static int scount = 0;
	static boolean b1 = true;

	public static void main(String ar[]) throws Exception {
		ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream("play1.txt"));
		InputStreamReader read = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(read);
		int n = Integer.parseInt(in.readLine());
		scount = n;
		for (int i = 0; i < n; i++) {
			String[] data = in.readLine().split(" ");
			out1.writeObject(new Song(data[0], data[1], Integer.parseInt(data[2])));
		}
		out1.flush();
		mmenu();
	}

	public static void mmenu() throws Exception {
		InputStreamReader read = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(read);
		System.out.println("Enter the playlist name");
		String file = in.readLine() + ".txt";
		System.out.println(count(file));
		secmenu(file);
	}

	public static void secmenu(String file) throws Exception {
		Scanner sc = new Scanner(System.in);
		while (b1) {
			System.out.println(
					"Select one of the following:\n1. Add\n2. Delete\n3. Search\n4. Show\n5. Back to Menu\n6. Exit");
			switch (sc.nextInt()) {
			case 1:
				System.out.println("Enter song credentials");
				add(file, sc.next(), sc.next(), sc.nextInt());
				System.out.println(count(file));
				break;
			case 2:
				System.out.println("Enter song name");
				String a=sc.next();
				String bx=search(file, a);
				if(bx.equals("false")) {
					throw new SongNotFoundException();
				}
				else {
					delete(file,a);
					System.out.println(count(file));
				}
				break;
			case 3:
				System.out.println("Enter song name");
				String b = search(file, sc.next());
				if (b.equals("false")) {
					throw new SongNotFoundException();
				}
				else {
					System.out.println(b);
				}
				break;
			case 4:
				show(file);
				break;
			case 5:
				mmenu();
				break;
			case 6:
				b1 = false;
			}
		}
	}

	public static void add(String file, String s3,String s2,int l) throws Exception {
		Song s=new Song(s3,s2,l);
		ObjectInputStream in1 = new ObjectInputStream(new FileInputStream(file));
		ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream("tmp.txt"));
		while (true) {
			try {
				Song s1;
				s1 = (Song) in1.readObject();
				out1.writeObject(s1);
				length++;
			} catch (EOFException e) {
				out1.writeObject(s);
				length++;
				break;
			}
		}
		in1.close();
		out1.close();
		ObjectInputStream in2 = new ObjectInputStream(new FileInputStream("tmp.txt"));
		ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(file));
		while (true) {
			try {
				Song s1 = (Song) in2.readObject();
				out2.writeObject(s1);
			} catch (EOFException e) {
				in2.close();
				out2.close();
				break;
			}
		}
	}

	public static void delete(String file, String n) throws Exception {
		ObjectInputStream in1 = new ObjectInputStream(new FileInputStream(file));
		ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream("tmp.txt"));
		while (true) {
			try {
				Song s1;
				s1 = (Song) in1.readObject();
				if (s1.name.equals(n) == false && s1.state == 1) {
					out1.writeObject(s1);
				}
			} catch (EOFException e) {
				in1.close();
				out1.close();
				break;
			}
		}
		ObjectInputStream in2 = new ObjectInputStream(new FileInputStream("tmp.txt"));
		ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(file));

		while (true) {
			try {
				Song s1;
				s1 = (Song) in2.readObject();
				out2.writeObject(s1);
			} catch (EOFException e) {
				in2.close();
				out2.close();
				break;
			}
		}
	}

	public static int count(String file) throws Exception {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		int count = 0;
		while (true) {
			try {
				Song s1;
				s1 = (Song) in.readObject();
				if (s1.state == 1) {
					count++;
				}
			} catch (EOFException e) {
				break;
			}
		}
		in.close();
		return count;
	}

	public static void show(String file) throws Exception {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		int count = 0;
		while (true) {
			try {
				Song s1;
				s1 = (Song) in.readObject();
				if (s1.state == 1) {
					count++;
					System.out.println(s1.toString());
				}
			} catch (EOFException e) {
				if (count == 0) {
					System.out.println("No Song Exist");
				}
				break;
			}
		}
		in.close();
	}

	public static String search(String file, String q) throws Exception {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		while (true) {
			try {
				Song s1;
				s1 = (Song) in.readObject();
				if (s1.name.equals(q) && s1.state == 1) {
					return(s1.toString());
				}
			} catch (EOFException e) {
				in.close();
				return ("false");
			}
		}
	}
}

class Song implements Serializable {
	private static final long serialVersionUID = 42L;
	String name, singer;
	int time, state;

	Song() {
		name = "";
		singer = "";
		time = 0;
		state = 1;
	}

	Song(String name, String singer, int time) {
		this.name = name;
		this.singer = singer;
		this.time = time;
		this.state = 1;
	}

	public void delete() {
		this.state = 0;
	}

	public String toString() {
		return (this.name + " " + this.singer + " " + this.time);
	}
}

class SongNotFoundException extends Exception {
	SongNotFoundException() {
		super("Song not found in the playlist");
	}
}