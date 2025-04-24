package com.inTrack.spring.service.EquipmentDBDetailService;

import com.inTrack.spring.dto.EquipmentColumnDetail;
import com.inTrack.spring.entity.EquipmentDBDetailEntity.EquipmentDetail;
import com.inTrack.spring.entity.EquipmentDBDetailEntity.GeneralEquipment;
import com.inTrack.spring.exception.SecurityError;
import com.inTrack.spring.exception.ValidationError;
import com.inTrack.spring.repository.EquipmentDBDetail.EquipmentDetailRepository;
import com.inTrack.spring.repository.EquipmentDBDetail.GeneralEquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EquipmentDBDetailService {

    private final DataSource dataSource;
    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String DATA_TYPE = "DATA_TYPE";
    public static final String TYPE_NAME = "TYPE_NAME";

    private final EquipmentDetailRepository equipmentDetailRepository;
    private final GeneralEquipmentRepository generalEquipmentRepository;


    public List<Map<String, String>> getTableColumnNamesAndTypes(final String tableName) {
        final List<Map<String, String>> columns = new ArrayList<>();

        try (final var connection = dataSource.getConnection()) {
            final DatabaseMetaData metaData = connection.getMetaData();

            // Get foreign key information for the table
            final Set<String> fkColumns = new HashSet<>();
            try (final ResultSet fkResultSet = metaData.getImportedKeys(null, null, this.convertToTableName(tableName))) {
                while (fkResultSet.next()) {
                    fkColumns.add(fkResultSet.getString("FKCOLUMN_NAME"));
                }
            }

            // Retrieve column details
            try (final ResultSet resultSet = metaData.getColumns(null, null, this.convertToTableName(tableName), null)) {
                while (resultSet.next()) {
                    String columnName = resultSet.getString(COLUMN_NAME);

                    // Skip foreign key columns
                    if (fkColumns.contains(columnName)) {
                        continue;
                    }

                    Map<String, String> columnDetails = new HashMap<>();
                    columnDetails.put(COLUMN_NAME, columnName);
                    columnDetails.put(DATA_TYPE, this.getDataType(resultSet.getString(TYPE_NAME)));
                    columns.add(columnDetails);
                }
            }
        } catch (final SQLException sqlException) {
            throw new SecurityError(sqlException.getMessage());
        } catch (final Exception e) {
            throw new ValidationError(e.getMessage());
        }
        return columns;
    }

    public List<Map<String, String>> getAgencyTableColumnNamesAndTypes(final String tableName) {
        final List<Map<String, String>> columns = new ArrayList<>();

        try (final var connection = dataSource.getConnection()) {
            final DatabaseMetaData metaData = connection.getMetaData();

            // Get foreign key information for the table
            final Set<String> fkColumns = new HashSet<>();
            try (final ResultSet fkResultSet = metaData.getImportedKeys(null, null, this.getAgencyTableName(tableName))) {
                while (fkResultSet.next()) {
                    fkColumns.add(fkResultSet.getString("FKCOLUMN_NAME"));
                }
            }

            // Retrieve column details
            try (final ResultSet resultSet = metaData.getColumns(null, null, this.getAgencyTableName(tableName), null)) {
                while (resultSet.next()) {
                    String columnName = resultSet.getString(COLUMN_NAME);

                    // Skip foreign key columns
                    if (fkColumns.contains(columnName)) {
                        continue;
                    }

                    Map<String, String> columnDetails = new HashMap<>();
                    columnDetails.put(COLUMN_NAME, columnName);
                    columnDetails.put(DATA_TYPE, this.getDataType(resultSet.getString(TYPE_NAME)));
                    columns.add(columnDetails);
                }
            }
        } catch (final SQLException sqlException) {
            throw new SecurityError(sqlException.getMessage());
        } catch (final Exception e) {
            throw new ValidationError(e.getMessage());
        }
        return columns;
    }

    private String getAgencyTableName(final String equipmentName) {
        String agencyTableName;
        switch (equipmentName.toUpperCase()) {
            case "BULKOXYGENSTORAGE":
                agencyTableName = "bulk_oxygen_storage_agency_info";
                break;
            default:
                throw new ValidationError("Equipment Name not found");
        }
        return agencyTableName;
    }


    private String getDataType(final String dataType) {
        String columnType;
        switch (dataType.toUpperCase()) {
            case "VARCHAR":
                columnType = "String";
                break;
            case "BIGINT":
                columnType = "Number";
                break;
            case "BIT":
                columnType = "Boolean";
                break;
            default:
                columnType = dataType;
                break;
        }
        return columnType.toUpperCase();
    }

    private String convertToTableName(final String className) {
        return className.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    public void addEquipmentDetail(final List<EquipmentDetail> equipmentDetails) {
        final List<EquipmentDetail> equipmentDetailList = new LinkedList<>();
        equipmentDetails.forEach(equipmentDetail -> {
            equipmentDetail.setEquipmentName(equipmentDetail.getEquipmentName().toUpperCase());
            equipmentDetailList.add(equipmentDetail);
        });
        this.equipmentDetailRepository.saveAll(equipmentDetailList);
    }

    public List<EquipmentDetail> getEquipmentDetail(final String equipmentName) {
        return this.equipmentDetailRepository.findByEquipmentName(equipmentName.toUpperCase());
    }

    public List<GeneralEquipment> getGeneralEquipmentDetail() {
        return this.generalEquipmentRepository.findAll();
    }

    public EquipmentColumnDetail getEquipmentColumnDetail(final String equipmentName) {
        final List<GeneralEquipment> generalEquipments = this.getGeneralEquipmentDetail();
        final List<EquipmentDetail> equipmentDetails = this.getEquipmentDetail(equipmentName);
        final List<Map<String, String>> agencyDetails = this.getAgencyTableColumnNamesAndTypes(equipmentName);
        final EquipmentColumnDetail equipmentColumnDetail = new EquipmentColumnDetail();
        equipmentColumnDetail.setGeneralEquipments(generalEquipments);
        equipmentColumnDetail.setEquipmentDetails(equipmentDetails);
        equipmentColumnDetail.setAgencyDetails(agencyDetails);
        return equipmentColumnDetail;
    }
}


