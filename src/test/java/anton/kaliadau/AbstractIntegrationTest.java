package anton.kaliadau;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@Testcontainers
public class AbstractIntegrationTest {

    protected final String coordinatorName1 = "Sakana";
    protected final String coordinatorName2 = "Maemi Tenma";
    protected final String studentName1 = "Pippa Pipkin";
    protected final String studentName2 = "Lumi Kaneko";
    protected final String studentName3 = "Lumi Kaneko";
    protected final String newName = "New Name";
    protected final String courseName1 = "Course Name 1";
    protected final String courseName2 = "Course Name 2";
    protected final String courseName3 = "Course Name 3";
    protected final Long id = 1L;


    @Container
    public static PostgreSQLContainer<?> postgreDBContainer;

    static {
        int containerPort = 5432;
        int localPort = 5435;
        DockerImageName postgres = DockerImageName.parse("postgres:13.15");
        postgreDBContainer = new PostgreSQLContainer<>(postgres)
                .withDatabaseName("postgres")
                .withUsername("postgres")
                .withPassword("123")
                .withReuse(true)
                .withExposedPorts(containerPort)
                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                        new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
                ))
                .withCopyFileToContainer(
                        MountableFile.forClasspathResource(
                                "init-db.sql"), "/docker-entrypoint-initdb.d/"
                );
        postgreDBContainer.start();
    }
}
