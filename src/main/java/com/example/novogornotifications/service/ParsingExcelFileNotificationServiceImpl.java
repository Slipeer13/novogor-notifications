package com.example.novogornotifications.service;

import com.example.novogornotifications.entity.Address;
import com.example.novogornotifications.entity.Notification;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@Service
public class ParsingExcelFileNotificationServiceImpl implements ParsingExcelFileNotificationService{

    private String nameAddress;
    private String info;
    private LocalDate dateDisabling;

    @Autowired
    private CheckAddressService checkAddressService;

    @Override
    public List<Address> getAddresses(MimeBodyPart part) throws MessagingException, IOException {
        List<Address> addresses = new ArrayList<>();
        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
            try (InputStream inputStream = part.getInputStream()) {
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                Sheet sheet = workbook.getSheetAt(0);
                //проходим по всему листу
                for (Row row : sheet) {
                    Iterator<Cell> cells = row.iterator();
                    while (cells.hasNext()) {
                        Cell cell = cells.next();
                        CellType cellType = cell.getCellType();
                        if (cellType == CellType.STRING) {
                            String value = cell.getStringCellValue();
                            if (value.equalsIgnoreCase("дата")) {
                                if (cells.hasNext()) {
                                    LocalDate cellValue = cells.next().getLocalDateTimeCellValue().toLocalDate();
                                    if (cellValue != null) {
                                        dateDisabling = cellValue;
                                    }
                                }
                            }
                            cell = cells.next();
                            value = cell.getStringCellValue();
                            if (value.equalsIgnoreCase("адрес")) {
                                if (cells.hasNext()) {
                                    String cellValue = cells.next().getStringCellValue().toLowerCase(Locale.ROOT);
                                    if (!cellValue.isEmpty()) {
                                        nameAddress = cellValue;
                                    }
                                }
                            }
                            cell = cells.next();
                            value = cell.getStringCellValue();
                            if (value.equalsIgnoreCase("инфо")) {
                                if (cells.hasNext()) {
                                    String cellValue = cells.next().getStringCellValue().toLowerCase(Locale.ROOT);
                                    if (!cellValue.isEmpty()) {
                                        info = cellValue;
                                    }
                                }
                            }
                            if(!nameAddress.isEmpty() && dateDisabling != null) {
                                Address address = new Address(nameAddress);
                                Notification notification = new Notification();
                                notification.setInfo(info);
                                notification.setDateDisabling(dateDisabling);
                                address.getNotifications().add(notification);
                                addresses.add(address);
                            }
                        }
                    }
                }
            }
        }
        return addresses;
    }
}
