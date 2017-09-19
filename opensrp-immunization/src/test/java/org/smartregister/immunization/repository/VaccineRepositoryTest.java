package org.smartregister.immunization.repository;


import android.content.ContentValues;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.smartregister.Context;
import org.smartregister.commonregistry.CommonFtsObject;
import org.smartregister.immunization.BaseUnitTest;
import org.smartregister.immunization.ImmunizationLibrary;
import org.smartregister.immunization.domain.Vaccine;
import org.smartregister.repository.Repository;
import org.smartregister.service.AlertService;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by onaio on 29/08/2017.
 */

public class VaccineRepositoryTest extends BaseUnitTest {

    @InjectMocks
    private VaccineRepository vaccineRepository;

    @Mock
    private Repository repository;

    @Mock
    private CommonFtsObject commonFtsObject;

    @Mock
    private AlertService alertService;

    @Mock
    private ImmunizationLibrary immunizationLibrary;

    @Mock
    private Context context;

    @Mock
    private SQLiteDatabase sqliteDatabase;

    @Before
    public void setUp() {

        initMocks(this);
        assertNotNull(vaccineRepository);
    }

    @Test
    public void addHyphenMethodAddsUnderscoreToString() throws Exception {
        String testString = vaccineRepository.addHyphen("test string");
        assertNotNull(testString);
        assertTrue(testString.contains("_"));
    }

    @Test
    public void removeHyphenMethodRemoveHyphenFromString() throws Exception {

        String testString = vaccineRepository.removeHyphen("test_string");
        assertNotNull(testString);
        assertFalse(testString.contains("-"));
    }

    @Test
    public void addHyphenMethodWithBlankParamReturnsBlankString() throws Exception {
        String testString = vaccineRepository.addHyphen("");
        assertNotNull(testString);
        assertTrue(testString.isEmpty());
    }

    @Test
    public void removeHyphenMethodWithBlankParamReturnsBlankString() throws Exception {
        String testString = vaccineRepository.removeHyphen("");
        assertNotNull(testString);
        assertTrue(testString.isEmpty());
    }

    @Test
    public void alertServiceDoesNotReturnNull() throws Exception {
        assertNotNull(vaccineRepository.alertService());
    }

    @Test
    public void instantiatesSuccessfullyOnConstructorCall() throws Exception {

        VaccineRepository vaccineRepository = new VaccineRepository(repository, commonFtsObject, alertService);
        assertNotNull(vaccineRepository);
    }

    @Test
    public void createTableCallsExecuteSQLMethod3Times() throws Exception {
        vaccineRepository.createTable(sqliteDatabase);
        Mockito.verify(sqliteDatabase, Mockito.times(3)).execSQL(anyString());

    }

    @Test
    public void addCallsDatabaseDatabaseMethod1TimesInCaseOfNonNullVaccineNullID() throws Exception {
        when(vaccineRepository.getWritableDatabase()).thenReturn(sqliteDatabase);
        vaccineRepository.add(new Vaccine());
        Mockito.verify(sqliteDatabase, Mockito.times(1)).insert(anyString(), isNull(String.class), any(ContentValues.class));
      }

    @Test
    public void addCallsDatabaseDatabaseMethod1TimesInCaseOfNonNullVaccineNotNullID() throws Exception {
        when(vaccineRepository.getWritableDatabase()).thenReturn(sqliteDatabase);
        Vaccine vaccine = new Vaccine();
        vaccine.setId(0l);
        vaccineRepository.add(vaccine);
        Mockito.verify(sqliteDatabase, Mockito.times(1)).update(anyString(), any(ContentValues.class), anyString(), any(String [].class));
    }

    @Test
    public void addCallsDatabaseDatabaseMethod0TimesInCaseOfNullVaccine() throws Exception {
        vaccineRepository.add(null);
        Mockito.verify(sqliteDatabase, Mockito.times(0)).insert(anyString(), (String)isNull(), any(ContentValues.class));
        Mockito.verify(sqliteDatabase, Mockito.times(0)).update(anyString(), any(ContentValues.class), anyString(), any(String [].class));
    }

    @Test
    public void updateCallsDatabaseUpdateMethod1Times() throws Exception {
        Vaccine vaccine = new Vaccine();
        vaccine.setId(0l);
        vaccineRepository.update(sqliteDatabase, vaccine);
        Mockito.verify(sqliteDatabase, Mockito.times(1)).update(anyString(), any(ContentValues.class), anyString(), any(String [].class));
    }

    @Test
    public void findbyEntityIDcallsDatabaseQueryMethod1Times() throws Exception {
        Cursor cursor = PowerMockito.mock(Cursor.class);
        when(sqliteDatabase.query(anyString(), any(String[].class), anyString(), any(String[].class), isNull(String.class), isNull(String.class), isNull(String.class), isNull(String.class))).thenReturn(cursor);
        when(vaccineRepository.getReadableDatabase()).thenReturn(sqliteDatabase);
        vaccineRepository.findByEntityId("entityID");
        Mockito.verify(sqliteDatabase, Mockito.times(1)).query(anyString(), any(String[].class), anyString(), any(String[].class), isNull(String.class), isNull(String.class), isNull(String.class), isNull(String.class));
    }

    @Test
    public void findbyCaseIDcallsDatabaseQueryMethod1Times() throws Exception {
        Cursor cursor = PowerMockito.mock(Cursor.class);
        when(sqliteDatabase.query(anyString(), any(String[].class), anyString(), any(String[].class), isNull(String.class), isNull(String.class), isNull(String.class), isNull(String.class))).thenReturn(cursor);
        when(vaccineRepository.getReadableDatabase()).thenReturn(sqliteDatabase);
       vaccineRepository.find(0l);
        Mockito.verify(sqliteDatabase, Mockito.times(1)).query(anyString(), any(String[].class), anyString(), any(String[].class), isNull(String.class), isNull(String.class), isNull(String.class), isNull(String.class));
    }

    @Test
    public void findbyUniqueIDcallsDatabaseQueryMethod1Times() throws Exception {
        Cursor cursor = PowerMockito.mock(Cursor.class);
        Vaccine vaccine = new Vaccine();
        vaccine.setFormSubmissionId("formsubmissionID");
        vaccine.setEventId("EventID");
        when(sqliteDatabase.query(anyString(), any(String[].class), anyString(), any(String[].class), isNull(String.class), isNull(String.class), anyString(),isNull(String.class))).thenReturn(cursor);
        when(vaccineRepository.getReadableDatabase()).thenReturn(sqliteDatabase);
        vaccineRepository.findUnique(sqliteDatabase,vaccine);
        Mockito.verify(sqliteDatabase, Mockito.times(1)).query(anyString(), any(String[].class), anyString(), any(String[].class), isNull(String.class), isNull(String.class), anyString(), isNull(String.class));
    }

    @Test
    public void deleteVaccineDatabaseDeleteMethod1Times() throws Exception {
        VaccineRepository vaccineRepositoryspy = Mockito.spy(vaccineRepository);
        Vaccine vaccine = new Vaccine();
        vaccine.setBaseEntityId("baseEntityID");
        vaccine.setName("name");
        vaccine.setFormSubmissionId("formsubmissionID");
        vaccine.setEventId("EventID");
        Mockito.doReturn(vaccine).when(vaccineRepositoryspy).find(0l);
        when(vaccineRepositoryspy.getWritableDatabase()).thenReturn(sqliteDatabase);
        vaccineRepositoryspy.deleteVaccine(0l);
        Mockito.verify(sqliteDatabase, Mockito.times(1)).delete(anyString(), anyString(), any(String[].class));
    }

    @Test
    public void closeMethodCallsInternalMethodsWithCorrectParams() throws Exception {
        when(vaccineRepository.getWritableDatabase()).thenReturn(sqliteDatabase);
        vaccineRepository.close(5l);
        Mockito.verify(vaccineRepository.getWritableDatabase(), Mockito.times(1)).update(eq(VaccineRepository.VACCINE_TABLE_NAME), (ContentValues) any(), anyString(), eq(new String[]{"5"}));
    }

    @Test
    public void closeMethodFailsSilentlyWithNullParams() throws Exception {
        when(vaccineRepository.getWritableDatabase()).thenReturn(sqliteDatabase);
        vaccineRepository.close(null);
        Mockito.verify(vaccineRepository.getWritableDatabase(), Mockito.times(0)).update(anyString(), (ContentValues) any(), anyString(), eq(new String[]{"5"}));

    }

}