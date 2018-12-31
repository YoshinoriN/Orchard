import org.mockito.Mockito._
import org.scalatest.FunSuite
import net.yoshinorin.selfouettie.models.Users
import net.yoshinorin.selfouettie.services.UsersService

class UsersServiceSpec extends FunSuite {

  val mockUsers: Users = mock(classOf[Users])
  val mockUsersService: UsersService = mock(classOf[UsersService])
  val user: Users = Users("YoshinoriN", 12345)

  when(mockUsersService.findByName("YoshinoriN"))
    .thenReturn(Some(user))

  when(mockUsersService.findByName("Yoshinori"))
    .thenReturn(None)

  test("user found") {
    assert(mockUsersService.findByName("YoshinoriN") == Some(user))
  }

  test("user not found") {
    assert(mockUsersService.findByName("Yoshinori") == None)
  }

}
