private void updateUserFile() {

    File file = new File("data/users.txt");
    StringBuilder updated = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

        String line;

        while ((line = br.readLine()) != null) {

            String[] data = line.split(",");

            if (data[1].equals(currentUser.username)) {
                data[4] = String.valueOf(currentUser.balance);
            }

            updated.append(String.join(",", data)).append("\n");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    try (FileWriter fw = new FileWriter(file)) {
        fw.write(updated.toString());
    } catch (Exception e) {
        e.printStackTrace();
    }
}