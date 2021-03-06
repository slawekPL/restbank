package pl.slawek.restbank.application

import pl.slawek.restbank.common.Money
import pl.slawek.restbank.domain.AccountNumber

import static pl.slawek.restbank.assertobject.AccountAssertObject.assertThat

class MakeOutgoingTransactionUseCaseTest extends BaseAccountSpecification {
    private MakeOutgoingTransactionUseCase useCase = new MakeOutgoingTransactionUseCase(accountRepository, transactionRepository)

    def "when account exist and amount is greater then 0 transaction should be saved"() {
        given:
        def accountNumber = AccountNumber.of("xzx")
        createAccountWithBalance500For(accountNumber)

        when:
        useCase.makeTransaction(new Transfer(accountNumber, AccountNumber.of("543"), Money.of(200)))

        then:
        def account = accountRepository.getBy(accountNumber)
        assertThat(account).hasBalance(Money.of(300))
    }

    def "when account does not exist should throw exception"() {
        given:
        def accountNumber = AccountNumber.of("xzx")

        when:
        useCase.makeTransaction(new Transfer(accountNumber, AccountNumber.of("543"), Money.of(131)))

        then:
        thrown(SourceAccountDoesNotExist)
    }

    def "when amount of transfer is equal 0 should throw exception"() {
        given:
        def source = AccountNumber.of("xzx")
        createAccountWithBalance500For(source)

        when:
        useCase.makeTransaction(new Transfer(source, AccountNumber.of("xzx"), Money.of(0)))

        then:
        thrown(CannotMakeTransferForZeroException)
    }
}
