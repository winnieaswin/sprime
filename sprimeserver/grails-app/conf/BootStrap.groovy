import sprime.server.Authority;
import sprime.server.Person;
import sprime.server.DeviceService;

class BootStrap {

     def authenticateService
     def deviceService

     def init = { servletContext ->

        def authority = new Authority();
        authority.authority = 'ROLE_USER';
        authority.description = 'Role of the User';
        authority.save(flush: true);

        def person = new Person();
        person.username = 'test';
        person.userRealName = 'test';
        person.passwd = authenticateService.encodePassword('test');
        person.enabled = true;
        person.emailShow = true;
        person.email = 'test@test.com';
        person.description = 'Test user';

        person.save(flush: true);

        authority.addToPeople(person);
        authority.save(flush: true);

        deviceService.createAndStartServer();
     }
     
     def destroy = {
     }
} 