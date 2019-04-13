

public class main {
    public static void main(String args[]) throws IOException{
        File studentInfo = new File("Student_Data/Student Info.xlsx");
        FileInputStream fis = new FileInputStream(studentInfo);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();

        while(rowIterator.hasNext()){
            Row row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();

            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                System.out.println(cell.toString());
            }
            System.out.println();
        }

        workbook.close();
        fis.close();
    }
}
