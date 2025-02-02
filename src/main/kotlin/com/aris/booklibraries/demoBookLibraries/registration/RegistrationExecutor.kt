package com.aris.booklibraries.demoBookLibraries.registration

import com.aris.booklibraries.demoBookLibraries.executors.AccountExecutor
import com.aris.booklibraries.demoBookLibraries.models.Account
import com.aris.booklibraries.demoBookLibraries.models.RegistrationDetails
import com.aris.booklibraries.demoBookLibraries.models.Role
import com.aris.booklibraries.demoBookLibraries.models.User
import com.aris.booklibraries.demoBookLibraries.models.response.ApiResponse
import com.aris.booklibraries.demoBookLibraries.registration.email.EmailSender
import com.aris.booklibraries.demoBookLibraries.registration.token.ConfirmationToken
import com.aris.booklibraries.demoBookLibraries.registration.token.ConfirmationTokenService
import com.aris.booklibraries.demoBookLibraries.services.AccountService
import com.aris.booklibraries.demoBookLibraries.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Component
class RegistrationExecutor {
    @Autowired
    lateinit var confirmationTokenService: ConfirmationTokenService
    @Autowired
    lateinit var emailSender: EmailSender
    @Autowired
    lateinit var emailValidator: EmailValidator
    @Autowired
    lateinit var accountService: AccountService
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var accountExecutor: AccountExecutor

    fun register(registrationDetails: RegistrationDetails): ApiResponse<String, String> {
        if ( registrationDetails.email.isNullOrEmpty() || registrationDetails.password.isNullOrEmpty()
            ||registrationDetails.firstName.isNullOrEmpty() ||registrationDetails.lastName.isNullOrEmpty() ){
            return ApiResponse(null, "Fill all the fields.")
        }

        val isValidEmail = emailValidator.test(registrationDetails.email!!)

        if ( !isValidEmail) {
            return ApiResponse(null, "Email not valid")
        }

        if ( accountService.findByEmail(registrationDetails.email!!) != null)
            return ApiResponse(null, "Email already exists.")

        val encoder = BCryptPasswordEncoder(10)
        val encodedPass = encoder.encode(registrationDetails.password)
        val accountToCreate = Account( null,registrationDetails.email,encodedPass,0, Role.USER.value)
        val createdAccount = accountService.save(accountToCreate)
            ?: return ApiResponse(null, "Something went wrong.")

        val token = UUID.randomUUID().toString()
        val confirmationToken = ConfirmationToken(
            null,
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            null,
            createdAccount
        )

        confirmationTokenService.save(confirmationToken)
        val userToCreate = User(null,registrationDetails.firstName,registrationDetails.lastName,createdAccount)
        userService.save(userToCreate)


        val link= "http://localhost:8080/signup/confirm?token=$token"
        emailSender.send(registrationDetails.email!!,
            buildEmail(registrationDetails.firstName!!,link))


        return ApiResponse(token, null)
    }

    @Transactional
    fun confirmToken(token: String): ApiResponse<String,String>{
        val confirmationToken: ConfirmationToken = confirmationTokenService.getToken(token)
            ?: return ApiResponse(null,"Where did u find that?")

        if (confirmationToken.confirmedAt != null) {
            return ApiResponse(null,"Email already confirmed.")
        }

        val expiredAt: LocalDateTime = confirmationToken.expiresAt

        if (expiredAt.isBefore(LocalDateTime.now())) {
            return ApiResponse(null,"Token is expired.")
        }

        confirmationTokenService.setConfirmedAt(token)
        accountExecutor.enableAccount(
            confirmationToken.account.email)
        return ApiResponse("confirmed",null)
    }

    private fun buildEmail(name: String, link: String): String {
        return """<div style="font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c">
            
            <span style="display:none;font-size:1px;color:#fff;max-height:0"></span>
            
              <table role="presentation" width="100%" style="border-collapse:collapse;min-width:100%;width:100%!important" cellpadding="0" cellspacing="0" border="0">
                <tbody><tr>
                  <td width="100%" height="53" bgcolor="#0b0c0c">
                    
                    <table role="presentation" width="100%" style="border-collapse:collapse;max-width:580px" cellpadding="0" cellspacing="0" border="0" align="center">
                      <tbody><tr>
                        <td width="70" bgcolor="#0b0c0c" valign="middle">
                            <table role="presentation" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse">
                              <tbody><tr>
                                <td style="padding-left:10px">
                              
                                </td>
                                <td style="font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px">
                                  <span style="font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block">Confirm your email</span>
                                </td>
                              </tr>
                            </tbody></table>
                          </a>
                        </td>
                      </tr>
                    </tbody></table>
                    
                  </td>
                </tr>
              </tbody></table>
              <table role="presentation" class="m_-6186904992287805515content" align="center" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse;max-width:580px;width:100%!important" width="100%">
                <tbody><tr>
                  <td width="10" height="10" valign="middle"></td>
                  <td>
                    
                            <table role="presentation" width="100%" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse">
                              <tbody><tr>
                                <td bgcolor="#1D70B8" width="100%" height="10"></td>
                              </tr>
                            </tbody></table>
                    
                  </td>
                  <td width="10" valign="middle" height="10"></td>
                </tr>
              </tbody></table>
            
            
            
              <table role="presentation" class="m_-6186904992287805515content" align="center" cellpadding="0" cellspacing="0" border="0" style="border-collapse:collapse;max-width:580px;width:100%!important" width="100%">
                <tbody><tr>
                  <td height="30"><br></td>
                </tr>
                <tr>
                  <td width="10" valign="middle"><br></td>
                  <td style="font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px">
                    
                        <p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c">Hi $name,</p><p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style="Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px"><p style="Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c"> <a href="$link">Activate Now</a> </p></blockquote>
             Link will expire in 15 minutes. <p>See you soon</p>        
                  </td>
                  <td width="10" valign="middle"><br></td>
                </tr>
                <tr>
                  <td height="30"><br></td>
                </tr>
              </tbody></table><div class="yj6qo"></div><div class="adL">
            
            </div></div>"""
    }
}