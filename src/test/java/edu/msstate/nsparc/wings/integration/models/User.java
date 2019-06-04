package edu.msstate.nsparc.wings.integration.models;

import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.administrative.*;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.employer.EmployerServiceEnrollment;
import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import edu.msstate.nsparc.wings.integration.models.participant.*;
import edu.msstate.nsparc.wings.integration.models.reports.BigRockReport;
import edu.msstate.nsparc.wings.integration.models.reports.DataIntegrityReport;
import edu.msstate.nsparc.wings.integration.models.trade.*;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingCourses;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.*;
import edu.msstate.nsparc.wings.integration.models.wia.WIAEnrollment;
import edu.msstate.nsparc.wings.integration.models.wia.WIATraining;
import edu.msstate.nsparc.wings.integration.models.wia.YouthGoal;

/**
 * Describes user entity
 * Created by a.vnuchko on 23.03.2016.
 */
public class User {
    private Roles role;
    private Participant participant;
    private ServiceEnrollment serviceEnrollment;
    private IndividualEmploymentPlan iep;
    private Everify everify;
    private CareerReadinessCertification crc;
    private CallIns callIns;
    private ProgramOutcomes prOutcomes;
    private StaffHome stHome;
    private RapidResponseEvent rre;
    private TradeEnrollment trd;
    private AtaaRtaaEnrollment atrt;
    private TrainingWaiver waiver;
    private Petition petit;
    private TradeTraining trTraining;
    private JobSearch jobSearch;
    private Relocation relocation;
    private DenialsTrade denTr;
    private WIAEnrollment wiaEnrl;
    private WIATraining wiaTrain;
    private YouthGoal youthGoal;
    private TrainingProvider trProvider;
    private TrainingCourses trCourses;
    private Notes note;
    private Assessment assessment;
    private DocumentUpload docUpload;
    private EmployerServiceEnrollment employerServiceEnrollment;
    private Contact contact;
    private Employer employer;
    private JobOrder order;
    private Services service;
	private Referrals referral;
    private ServiceCenters serviceCenters;
    private LWIA lwia;
    private Staff staff;
    private BigRockReport bgr;
    private DataIntegrityReport dgr;

    /**
     * Default constructor.
     * @param newRole - user role
     */
    public User(Roles newRole) {
        this.role = newRole;
        participant = new Participant(newRole);
        serviceEnrollment = new ServiceEnrollment(newRole);
        iep = new IndividualEmploymentPlan(newRole);
        everify = new Everify(newRole);
        crc = new CareerReadinessCertification(newRole);
        callIns = new CallIns(newRole);
        prOutcomes = new ProgramOutcomes(newRole);
        stHome = new StaffHome(newRole);
        rre = new RapidResponseEvent(newRole);
        trd = new TradeEnrollment(newRole);
        atrt = new AtaaRtaaEnrollment(newRole);
        waiver = new TrainingWaiver(newRole);
        petit = new Petition(newRole);
        trTraining = new TradeTraining(newRole);
        jobSearch = new JobSearch(newRole);
        relocation = new Relocation(newRole);
        denTr = new DenialsTrade(newRole);
        wiaEnrl = new WIAEnrollment(newRole);
        wiaTrain = new WIATraining(newRole);
        youthGoal = new YouthGoal(newRole);
        trProvider = new TrainingProvider(newRole);
        trCourses = new TrainingCourses(newRole);
        note = new Notes(newRole);
        assessment = new Assessment(newRole);
        docUpload = new DocumentUpload(newRole);
        employerServiceEnrollment = new EmployerServiceEnrollment(newRole);
        contact = new Contact(newRole);
        employer = new Employer(newRole);
        order = new JobOrder(newRole);
        service = new Services(newRole);
        referral = new Referrals(newRole);
        serviceCenters = new ServiceCenters(newRole);
        lwia = new LWIA(newRole);
        staff = new Staff(newRole);
        bgr = new BigRockReport(newRole);
        dgr = new DataIntegrityReport(newRole);
    }

    public void setNewUser(Roles newRole) {
        this.role = newRole;
        this.participant = new Participant(newRole);
        this.serviceEnrollment = new ServiceEnrollment(newRole);
        this.iep = new IndividualEmploymentPlan(newRole);
        this.everify = new Everify(newRole);
        this.crc = new CareerReadinessCertification(newRole);
        this.callIns = new CallIns(newRole);
        this.prOutcomes = new ProgramOutcomes(newRole);
        this.stHome = new StaffHome(newRole);
        this.rre = new RapidResponseEvent(newRole);
        this.trd = new TradeEnrollment(newRole);
        this.atrt = new AtaaRtaaEnrollment(newRole);
        this.waiver = new TrainingWaiver(newRole);
        this.petit = new Petition(newRole);
        this.trTraining = new TradeTraining(newRole);
        this.jobSearch = new JobSearch(newRole);
        this.relocation = new Relocation(newRole);
        this.denTr = new DenialsTrade(newRole);
        this.wiaEnrl = new WIAEnrollment(newRole);
        this.wiaTrain = new WIATraining(newRole);
        this.youthGoal = new YouthGoal(newRole);
        this.trProvider = new TrainingProvider(newRole);
        this.trCourses = new TrainingCourses(newRole);
        this.note = new Notes(newRole);
        this.assessment = new Assessment(newRole);
        this.docUpload = new DocumentUpload(newRole);
        this.employerServiceEnrollment = new EmployerServiceEnrollment(newRole);
        this.contact = new Contact(newRole);
        this.employer = new Employer(newRole);
        this.order = new JobOrder(newRole);
        this.service = new Services(newRole);
        this.referral = new Referrals(newRole);
        this.serviceCenters = new ServiceCenters(newRole);
        this.lwia = new LWIA(newRole);
        this.staff = new Staff(newRole);
        this.bgr = new BigRockReport(newRole);
        this.dgr = new DataIntegrityReport(newRole);
    }

    public String getRoleString() {
        return role.toString();
    }

    public Participant getParticipant() {
        return participant;
    }

    public ServiceEnrollment getServiceEnrollment() {
        return serviceEnrollment;
    }

    public IndividualEmploymentPlan getIEP() {
        return iep;
    }

    public Everify getEverify() {
        return everify;
    }

    public CareerReadinessCertification getCRC() {
        return crc;
    }

    public CallIns getCallIns() {
        return callIns;
    }

    public ProgramOutcomes getPrOutcomes() {
        return prOutcomes;
    }

    public StaffHome getStHome() {
        return stHome;
    }

    public RapidResponseEvent getRre() {
        return rre;
    }

    public TradeEnrollment getTradeEnrollment() {
        return trd;
    }

    public AtaaRtaaEnrollment getAtrt() {
        return atrt;
    }

    public TrainingWaiver getTrainingWaiver() {
        return waiver;
    }

    public Petition getPetition() {
        return petit;
    }

    public TradeTraining getTradeTraining() {
        return  trTraining;
    }

    public JobSearch getJobSearch() {
        return jobSearch;
    }

    public Relocation getRelocation() {
        return relocation;
    }

    public DenialsTrade getDenTr() {
        return denTr;
    }

    public WIAEnrollment getWiaEnrollment() {
        return wiaEnrl;
    }

    public WIATraining getWiaTrain() {
        return wiaTrain;
    }

    public YouthGoal getYouthGoal() {
        return youthGoal;
    }

    public TrainingProvider getTrProvider() {
        return trProvider;
    }

    public TrainingCourses getTrainCourses() {
        return trCourses;
    }

    public Notes getNote() {
        return note;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public DocumentUpload getDocUpload() {
        return docUpload;
    }

    public EmployerServiceEnrollment getEmployerServiceEnrollment() {
        return employerServiceEnrollment;
    }

    public Contact getContact() {
        return contact;
    }

    public Employer getEmployer() {
        return employer;
    }

    public JobOrder getOrder() {
        return order;
    }

    public Roles getRole() {
        return role;
    }

    public Services getService() {
        return service;
    }

	public Referrals getReferral() {
        return referral;
    }

    public ServiceCenters getServiceCenters() {
        return serviceCenters;
    }

    public LWIA getLwia() {
        return lwia;
    }

    public Staff getStaff() {
        return staff;
    }

    public BigRockReport getBgReport() {
        return bgr;
    }

    public DataIntegrityReport getDataIntegrityReport() {
        return dgr;
    }
}
