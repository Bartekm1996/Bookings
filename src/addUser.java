import java.io.*;
import java.util.*;

public class addUser {

	private static final String COMMA = ",";
	private static final String NEW_LINE = "\n";
	
	public static Hashtable<String, User> users = new Hashtable<String, User>();
	/**
	 * userNameEmail stores email corresponding to usernames which allow the user
	 * to login using either email or username
	 */
	public static Hashtable<String, String> userNameEmail = new Hashtable<String, String>();
	public static Hashtable<String, User> admins = new Hashtable<String, User>();
	public static Hashtable<String, String> adminsEmails = new Hashtable<String, String>();
	public static Set<User> userListView = new HashSet<User>();
	public static Set<String> usersEmails = new HashSet<String>();
	public static Map<String, TreeSet<Booking>> userBookings = new HashMap<String, TreeSet<Booking>>();
	public static Map<String, TreeSet<Booking>> facilityBooking = new HashMap<String, TreeSet<Booking>>();
	public static Hashtable<String, String> facilityPrices = new Hashtable<String, String>();
	public static Hashtable<String, Booking> Bookigns = new Hashtable<>();
	public static Hashtable<String, Hashtable<String, TreeSet<Booking>>> facilityDateBookings = new Hashtable<>();
	public static ArrayList<facility> decomisionedFacilites = new ArrayList<facility>();
	public static ArrayList<String> blockedUsers = new ArrayList<String>();
	public static Set<String> facilitesList = new HashSet<>();
	public static Hashtable<String, facility> facilitesId = new Hashtable<String, facility>();
	public static Set<facility> facilites = new TreeSet<>(new facilityComparator()); // TreeSet with a custom comparator which compares
																					// Facilities by its names			
	public static ArrayList<String> dates = new ArrayList<>();
	public static Hashtable<String, Hashtable<String,ArrayList<userActivity>>> userAcity = new Hashtable<>();
	public static HashMap<String, String> forgotPasswords = new HashMap<>();
	public static Set<String> passwordRequest = new TreeSet<String>();
	private static String userDir = System.getProperty("user.home");
	private static File core = new File(userDir, ".corejava");

	/**
	 * Method updates userAccounts after changes made by the user
	 */
	public static void saveEditedAccounts() {

		File userFile = new File(core, "User.csv");
		if(userFile.exists()) {
			userFile.delete();
		}

		for (User user : userListView) {
			writeCsvFile(user, "user");
		}
	}

	public static void writeCsvFile(User leaugememeber, String admin) {

		File userFile = null;
		BufferedWriter fileWriter = null;

		switch (admin) {
		case "admin":
			userFile = new File(core, "Admin.csv");
			break;
		case "user":
			userFile = new File(core, "User.csv");
			break;
		}


		try {

			if (!userFile.exists()) {
				userFile.createNewFile();
			}
			if(admin.equals("admin")) {
				fileWriter = new BufferedWriter(new FileWriter(userFile, true));
				fileWriter.append(leaugememeber.getUserName());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getName());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getSurName());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getEmail());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getDateOfBirth());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getPicPath());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getPassword());
				fileWriter.append(NEW_LINE);
			}else{
				fileWriter = new BufferedWriter(new FileWriter(userFile, true));
				fileWriter.append(leaugememeber.getUserName());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getName());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getSurName());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getEmail());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getDateOfBirth());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getPicPath());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getPassword());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getAccountBalance());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getPreviouseBalance());
				fileWriter.append(COMMA);
				fileWriter.append(leaugememeber.getRequest());
				fileWriter.append(NEW_LINE);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		} finally {
			try {

				fileWriter.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}

	}

	public static void readUserCsvFile() {

		File userFile = new File(core, "User.csv");

		if (userFile.exists()) {

			final int USERNAME = 0;
			final int NAME = 1;
			final int SURNAME = 2;
			final int EMAIL = 3;
			final int DATEOFBIRTH = 4;
			final int PICTURE = 5;
			final int PASSWORD = 6;
			final int CURRENTBALANCE = 7;
			final int PREVIOUSBLANCE = 8;
			final int REQUEST = 9;

			BufferedReader fileReader = null;

			try {
				
				String comma = "";
				fileReader = new BufferedReader(new FileReader(userFile));
				while ((comma = fileReader.readLine()) != null) {
					String[] detail = comma.split(COMMA);
					if (detail.length > 0) {
						userNameEmail.put(detail[USERNAME], detail[EMAIL]);
						usersEmails.add(detail[EMAIL]);
						users.put(detail[EMAIL], new User(detail[USERNAME], detail[NAME], detail[SURNAME],
								detail[EMAIL], detail[DATEOFBIRTH], detail[PICTURE], detail[PASSWORD],detail[CURRENTBALANCE],detail[PREVIOUSBLANCE],detail[REQUEST]));
						userListView.add(new User(detail[USERNAME], detail[NAME], detail[SURNAME], detail[EMAIL],
								detail[DATEOFBIRTH], detail[PICTURE], detail[PASSWORD], detail[CURRENTBALANCE],detail[PREVIOUSBLANCE],detail[REQUEST]));
						userBookings.put(detail[EMAIL], new TreeSet<Booking>(new BookingCompare()));
						userAcity.put(detail[EMAIL], new Hashtable<>());
					}
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			} finally {
				try {
					fileReader.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	public static void updateDecomisionedFacilites() {
		File decomisioned = new File(core, "DecomisionedFacilites.csv");
		if(decomisioned.exists())decomisioned.delete();
		try {
			decomisioned.createNewFile();
		}catch(IOException e) {
			e.printStackTrace();
		}
  		for(facility facility : addUser.decomisionedFacilites) {
			  addUser.writeDecomisionedFacilites(facility.getFacilityId(),facility.getFacility(),facility.getDateFrom(),facility.getDateUntil());
		}

	}
	
	public static void readAdminCsvFile() {

		File adminFile = new File(core, "Admin.csv");
		final int USERNAME = 0;
		final int NAME = 1;
		final int SURNAME = 2;
		final int EMAIL = 3;
		final int DATEOFBIRTH = 4;
		final int PICTURE = 5;
		final int PASSWORD = 6;

		BufferedReader fileReader = null;
		if (adminFile.exists()) {
			try {

				String comma = "";
				fileReader = new BufferedReader(new FileReader(adminFile));
				while ((comma = fileReader.readLine()) != null) {
					String[] detailss = comma.split(COMMA);
					if (detailss.length > 0) {
						admins.put(detailss[USERNAME], new User(detailss[USERNAME], detailss[NAME], detailss[SURNAME],
								detailss[EMAIL], detailss[DATEOFBIRTH], detailss[PICTURE], detailss[PASSWORD], "null","null","null"));
						adminsEmails.put(detailss[EMAIL], detailss[USERNAME]);
						userBookings.put(detailss[USERNAME], new TreeSet<>(new BookingCompare()));
					}
				}

			} catch (IOException exception) {
				exception.printStackTrace();
			} finally {
				try {
					fileReader.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	public static void addblockedUserFile(String username, String request) {
		File user = new File(core, "BlockedUsers.csv");
		BufferedWriter fileWriter = null;
		try {

			if (!user.exists()) {
				user.createNewFile();
			}
				fileWriter = new BufferedWriter(new FileWriter(user, true));
				fileWriter.append(username);
				fileWriter.append(COMMA);
				fileWriter.append(request);
				fileWriter.append(NEW_LINE);
			
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {

				fileWriter.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

	public static void readBlockedUsers() {

		File blockedUsersFile = new File(core, "BlockedUsers.csv");

		final int blockedUser = 0;
		final int REQUEST = 1;
		BufferedReader fileReader = null;

		if (blockedUsersFile.exists()) {
			try {

				String SPACE = "";
				fileReader = new BufferedReader(new FileReader(blockedUsersFile));
				while ((SPACE = fileReader.readLine()) != null) {
					String[] detailss = SPACE.split(COMMA);

					if (detailss.length > 0)

						if (detailss[REQUEST].equals("Yes")) {
							passwordRequest.add(detailss[blockedUser]);
						} else {
							blockedUsers.add(detailss[blockedUser]);
						}
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			} finally {
				try {
					fileReader.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	public static void readFacilityPrices() {

		File facilityPrice = new File(core, "Prices.csv");
		if (facilityPrice.exists()) {

			final int FACILITY = 0;
			final int PRICE = 1;

			BufferedReader fileReader = null;

			try {

				String comma = "";
				fileReader = new BufferedReader(new FileReader(facilityPrice));
				while ((comma = fileReader.readLine()) != null) {
					String[] detail = comma.split(COMMA);
					if (detail.length > 0) {

						facilityPrices.put(detail[FACILITY], detail[PRICE]);
					}
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			} finally {
				try {
					fileReader.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	public static void writeFacilityPrice(String facility, String price) {

		File facilityPrices = new File(core, "Prices.csv");
		BufferedWriter allBookingsWriter = null;
		try {
			if (!facilityPrices.exists()) {
				facilityPrices.createNewFile();
			}
				allBookingsWriter = new BufferedWriter(new FileWriter(facilityPrices, true));
				allBookingsWriter.append(facility);
				allBookingsWriter.append(COMMA);
				allBookingsWriter.append(price);
				allBookingsWriter.append(NEW_LINE);

		}catch (IOException exception) {

			exception.printStackTrace();
		}finally {
			try {
				allBookingsWriter.close();
			} catch (IOException exception) {

				exception.printStackTrace();

			}
		}

	}

	public static void readDecomisionedFacilites() {

		File decomisioned = new File(core, "DecomisionedFacilites.csv");
		if (decomisioned.exists()) {

			final int FACILITYID = 0;
			final int FACILITY = 1;
			final int DATEFROM = 2;
			final int DATEUNTIL = 3;

			BufferedReader fileReader = null;

			try {

				String comma = "";
				fileReader = new BufferedReader(new FileReader(decomisioned));
				while ((comma = fileReader.readLine()) != null) {
					String[] detail = comma.split(COMMA);
					if (detail.length > 0) {

						if (facilitesId.containsKey(detail[FACILITYID])) {
							decomisionedFacilites.add(new facility(detail[FACILITYID],detail[FACILITY],detail[DATEFROM],detail[DATEUNTIL]));
							facilitesId.get(detail[FACILITYID]).setDateFrom(detail[DATEFROM]);
							facilitesId.get(detail[FACILITYID]).setDateUntil(detail[DATEUNTIL]);
						}

					}
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			} finally {
				try {
					fileReader.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}

	}

	public static void writeDecomisionedFacilites(String facilityId, String facility, String startDate,
			String endDate) {

		File decomisioned = new File(core, "DecomisionedFacilites.csv");

		BufferedWriter allBookingsWriter = null;

		try {

			if (!decomisioned.exists()) {
				decomisioned.createNewFile();
		
			}
				allBookingsWriter = new BufferedWriter(new FileWriter(decomisioned, true));
				allBookingsWriter.append(facilityId);
				allBookingsWriter.append(COMMA);
				allBookingsWriter.append(facility);
				allBookingsWriter.append(COMMA);
				allBookingsWriter.append(startDate);
				allBookingsWriter.append(COMMA);
				allBookingsWriter.append(endDate);
				allBookingsWriter.append(NEW_LINE);
		} catch (IOException exception) {

			exception.printStackTrace();

		} finally {
			try {

				allBookingsWriter.close();

			} catch (IOException exception) {

				exception.printStackTrace();

			}
		}

	}

	public static void writeBookings(Booking booking, String facility, String facilityId) {

		File allBookings = new File(core, "Bookings");
		if (!allBookings.exists())allBookings.mkdir();
		File facilityBookings = new File(allBookings,facility+"-"+facilityId+".csv");
		
		try {
			if(!facilityBookings.exists()) {
			facilityBookings.createNewFile();}
	} catch (IOException e) {
		e.printStackTrace();
	}
		BufferedWriter allBookingsWriter = null;		
		if (facilityBookings.exists()) {
			try {


					allBookingsWriter = new BufferedWriter(new FileWriter(facilityBookings, true));
					allBookingsWriter.append(booking.getFacility());
					allBookingsWriter.append(COMMA);
					allBookingsWriter.append(booking.getBookingPurpose());
					allBookingsWriter.append(COMMA);
					allBookingsWriter.append(booking.getUserName());
					allBookingsWriter.append(COMMA);
					allBookingsWriter.append(booking.getDates());
					allBookingsWriter.append(COMMA);
					allBookingsWriter.append(booking.getBookingId());
					allBookingsWriter.append(COMMA);
					allBookingsWriter.append(Integer.toString(booking.getStartTime()));
					allBookingsWriter.append(COMMA);
					allBookingsWriter.append(Integer.toString(booking.getEndTime()));
					allBookingsWriter.append(COMMA);
					allBookingsWriter.append(booking.getDuration());
					allBookingsWriter.append(COMMA);
					allBookingsWriter.append(booking.getDue());
					allBookingsWriter.append(COMMA);
					allBookingsWriter.append(booking.getMadeOn());
					allBookingsWriter.append(NEW_LINE);

				

			} catch (IOException exception) {

				exception.printStackTrace();

			} finally {
				try {

					allBookingsWriter.close();

				} catch (IOException exception) {

					exception.printStackTrace();

				}
			}
		}
	}

	/**
	 * Method initiates lists and set by reading the name and id
	 * of the facility file
	 */
	public static void listFacilites() {

		File allBookings = new File(core, "Bookings");
		if (allBookings.exists()) {
			File[] facilits = allBookings.listFiles();

			for (File facility : facilits) {
				if (facility.getName().contains(".")) {
					String[] names = facility.getName().split("\\.");
					String[] id = names[0].split("-");
					facilites.add(new facility(id[1], id[0], null, null));
					facilityBooking.put(id[0], new TreeSet<>(new BookingCompare()));
					facilityDateBookings.put(id[0], new Hashtable<>());
					facilitesId.put(id[1], new facility(id[1], id[0], null, null));
					facilitesList.add(id[0]);
					readFacilityBookings(names[0]);

				} else {

					String[] id = facility.getName().split("-");
					facilites.add(new facility(id[1], id[0], null, null));
					facilityBooking.put(id[0], new TreeSet<>(new BookingCompare()));
					facilityDateBookings.put(id[0], new Hashtable<>());
					facilitesId.put(id[1], new facility(id[1], id[0], null, null));
					facilitesList.add(id[0]);
					readFacilityBookings(facility.getName());

				}
			}
		}

	}

	/**
	 * Method updates,clears and initiates all data required   
	 */
	public static void initEverything() {

		updateBookings();
		users.clear();
		userListView.clear();
		usersEmails.clear();
		userBookings.clear();
		blockedUsers.clear();
		userAcity.clear();
		userNameEmail.clear();
		admins.clear();
		adminsEmails.clear();
		facilityPrices.clear();
		decomisionedFacilites.clear();
		dates.clear();
		forgotPasswords.clear();
		passwordRequest.clear();
		Bookigns.clear();
		tableLeauge.flowPane.getChildren().clear();
		facilites.clear();
		facilitesList.clear();
		facilityDateBookings.clear();
		facilityBooking.clear();

		
		readBlockedUsers();
		readUserCsvFile();
		readAdminCsvFile();
		listFacilites();
		readFacilityPrices();
		readUserActivity();
		readDecomisionedFacilites();
		initDate();
		
		for(facility facility :  facilites ) {
			tableLeauge.flowPane.getChildren().add(facility.getButton());
		}
	}

	public static void addFacility(String facility, String facilityId) {

		File allBookings = new File(core, "Bookings");
		if (!allBookings.exists())
			allBookings.mkdir();

		File facilityFile = new File(allBookings, facility + "-" + facilityId + ".csv");

		if (!facilityFile.exists()) {
			try {
				facilityBooking.put(facility, new TreeSet<>(new BookingCompare()));
				facilityFile.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}

	public static boolean deleteFacility(String facility) {
		File allBookings = new File(core, "Bookings");
		File facilityFile = new File(allBookings, facility);
		if (facilityFile.exists()) {
			facilityFile.delete();
			return true;
		}

		return false;
	}

	public static void writeUserActivity(userActivity activity, String username) {

		File user = new File(core, "userFile");
		if (!user.exists())
			user.mkdir();
		File userDir = new File(user, username + ".csv");

		BufferedWriter userActivityWriter = null;
		if(addUser.users.containsKey(userLogin.getCurrentUser())) {
		if(addUser.userAcity.get(username).containsKey(activity.getDate())) {
			userAcity.get(username).get(activity.getDate()).add(new userActivity(activity.getDate(), activity.getActivity(),
					activity.getOperation(), activity.getAmount(),activity.getPrice()));
		}else {
			userAcity.get(username).put(activity.getDate(), new ArrayList<userActivity>());
			userAcity.get(username).get(activity.getDate()).add(new userActivity(activity.getDate(), activity.getActivity(),
					activity.getOperation(), activity.getAmount(),activity.getPrice()));
		}}
		try {

			if (!userDir.exists()) {
				userDir.createNewFile();
			}
				userActivityWriter = new BufferedWriter(new FileWriter(userDir, true));
				userActivityWriter.append(activity.getDate());
				userActivityWriter.append(COMMA);
				userActivityWriter.append(activity.getActivity());
				userActivityWriter.append(COMMA);
				userActivityWriter.append(activity.getOperation());
				userActivityWriter.append(COMMA);
				userActivityWriter.append(activity.getAmount());
				userActivityWriter.append(COMMA);
				userActivityWriter.append(activity.getPrice());
				userActivityWriter.append(NEW_LINE);

			}

		catch (IOException exce) {
			exce.printStackTrace();
		} finally {
			try {
				userActivityWriter.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}

	}

	public static void readUserActivity() {
		for (User user : userListView) {
			readUserActivity(user);
		}
	}

	private static void readUserActivity(User user) {

		File activtiy = new File(core, "userFile");
		File userActi = new File(activtiy, user.getEmail() + ".csv");

		final int DATE = 0;
		final int ACTIVITY = 1;
		final int BALANCE = 2;
		final int AMOUNT = 3;
		final int PRICE = 4;

		BufferedReader fileReader = null;

		if (userActi.exists()) {
			try {

				String SPACE = "";
				fileReader = new BufferedReader(new FileReader(userActi));

				while ((SPACE = fileReader.readLine()) != null) {
					String[] detailss = SPACE.split(COMMA);

					if (detailss.length > 0) {

						String[] split = detailss[DATE].split(" ");
						String date = split[0] + "-" + split[1] + "-" + split[2];
					
						if(addUser.userAcity.get(user.getEmail()).containsKey(date)) {
							userAcity.get(user.getEmail()).get(date).add(new userActivity(date, detailss[ACTIVITY],
									detailss[BALANCE], detailss[AMOUNT],detailss[PRICE]));
						}else {
							userAcity.get(user.getEmail()).put(date, new ArrayList<userActivity>());
							userAcity.get(user.getEmail()).get(date).add(new userActivity(date, detailss[ACTIVITY],
									detailss[BALANCE], detailss[AMOUNT],detailss[PRICE]));
						}
					

					}
				}
			}

			catch (IOException exception) {
				exception.printStackTrace();
			} finally {
				try {
					fileReader.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}

	}

	private static void readFacilityBookings(String facility) {

		File allBookings = new File(core, "Bookings");
		File bookings = new File(allBookings, facility + ".csv");

		final int FACILITY = 0;
		final int BOOKINGPURPOSE = 1;
		final int EMAIL = 2;
		final int DATE = 3;
		final int BOOKINGID = 4;
		final int STARTIME = 5;
		final int ENDTIME = 6;
		final int DUARTION = 7;
		final int AMOUNT = 8;
		final int MADEON = 9;

		BufferedReader fileReader = null;

		if (bookings.exists()) {
			try {

				String SPACE = "";
				fileReader = new BufferedReader(new FileReader(bookings));
				while ((SPACE = fileReader.readLine()) != null) {
					String[] detailss = SPACE.split(COMMA);
					if (detailss.length > 0) {

						userBookings.get(detailss[EMAIL])
								.add(new Booking(detailss[FACILITY], detailss[BOOKINGPURPOSE], detailss[EMAIL],
										detailss[DATE].replaceAll("/", "-"), detailss[BOOKINGID],
										Integer.parseInt(detailss[STARTIME]), Integer.parseInt(detailss[ENDTIME]),
										detailss[DUARTION], detailss[AMOUNT], detailss[MADEON]));
					}

					facilityBooking.get(detailss[FACILITY])
							.add(new Booking(detailss[FACILITY], detailss[BOOKINGPURPOSE], detailss[EMAIL],
									detailss[DATE].replaceAll("/", "-"), detailss[BOOKINGID],
									Integer.parseInt(detailss[STARTIME]), Integer.parseInt(detailss[ENDTIME]),
									detailss[DUARTION], detailss[AMOUNT], detailss[MADEON]));

					Bookigns.put(detailss[BOOKINGID],
							new Booking(detailss[FACILITY], detailss[BOOKINGPURPOSE], detailss[EMAIL],
									detailss[DATE].replaceAll("/", "-"), detailss[BOOKINGID],
									Integer.parseInt(detailss[STARTIME]), Integer.parseInt(detailss[ENDTIME]),
									detailss[DUARTION], detailss[AMOUNT], detailss[MADEON]));

					facilityDateBookings.get(detailss[FACILITY]).put(detailss[DATE], new TreeSet<>(new BookingCompare()));

					dates.add(detailss[DATE]);

				}
			}

			catch (IOException exception) {
				exception.printStackTrace();
			} finally {
				try {
					fileReader.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}

	}

	private static void initDate() {
 
		int i = 0;
		while(i < dates.size()) {
		for (String facility : facilitesList) {
			for (Booking booking : facilityBooking.get(facility)) {
				if(booking.getDates().equals(dates.get(i))) {
					facilityDateBookings.get(facility).get(dates.get(i)).add(booking);
				}
			}
		}
		i++;
		}
		
		while(i < dates.size()) {
			for (User user : userListView) {
				addUser.userAcity.get(user.getEmail()).put(dates.get(i), new ArrayList<>());
			}
			i++;
		}

	}

	public static void updateBookings() {

		for(facility facility : facilites) {
			File file = new File(core,"Bookings");
			File facilityBookings = new File(file,facility.getFacility()+"-"+facility.getFacilityId()+".csv");
			if(facilityBookings.exists()) {facilityBookings.delete();}
			try {
				facilityBookings.createNewFile();
			}catch(IOException e) {
				e.printStackTrace();
			}
			for(Booking booking : facilityBooking.get(facility.getFacility())) {
				addUser.writeBookings(booking, facility.getFacility(), facility.getFacilityId());
			}
		}
	}

	public static void writeUpdatePassword(String email, String password) {
		File users = new File(core, "ForgotPasswordUsers.csv");
		boolean created = false;

		try {
			users.createNewFile();
			created = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		String header = "Email,New Password";
		BufferedWriter fileWritter = null;
		if (!users.exists())
			;
		try {
			if (created) {
				fileWritter = new BufferedWriter(new FileWriter(users));
				fileWritter.append(header);
				fileWritter.append(NEW_LINE);
				fileWritter.append(email);
				fileWritter.append(COMMA);
				fileWritter.append(password);
				fileWritter.append(NEW_LINE);

			} else {

				fileWritter = new BufferedWriter(new FileWriter(users, true));
				fileWritter.append(email);
				fileWritter.append(COMMA);
				fileWritter.append(password);
				fileWritter.append(NEW_LINE);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWritter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void readForgotPassword() {

		File users = new File(core, "ForgotPasswordUsers.csv");
		BufferedReader reader = null;
		if (users.exists()) {

			final int USER = 0;
			final int PASSWORD = 1;

			try {

				String space = "";
				reader = new BufferedReader(new FileReader(users));
				while ((space = reader.readLine()) != null) {

					String[] reads = space.split(COMMA);
					forgotPasswords.put(reads[USER], reads[PASSWORD]);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void updateBlockedUser() {
	
		File users = new File(core, "BlockedUsers.csv");
		users.delete();
		for (User user : userListView) {
			if (forgotPasswords.containsKey(user.getEmail())) {
				addblockedUserFile(user.getEmail(), forgotPasswords.get(user.getEmail()));
			}
		}
	}
}
