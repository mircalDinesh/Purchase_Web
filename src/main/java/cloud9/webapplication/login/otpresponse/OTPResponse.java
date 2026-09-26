package cloud9.webapplication.login.otpresponse;

public record OTPResponse(
        String email,
        String ledgerName,
        String role,
        String taxMode,
        String taxType
) {
}
