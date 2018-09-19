package hmda.validation.engine

import hmda.model.filing.lar.LoanApplicationRegister
import hmda.validation.context.ValidationContext
import hmda.validation.rules.lar.quality._
import hmda.validation.rules.lar.syntactical.S300
import hmda.validation.rules.lar.validity._

object LarEngine extends ValidationEngine[LoanApplicationRegister] {

  override def syntacticalChecks(ctx: ValidationContext) = Vector(
    S300
  )

  override val validityChecks = Vector(
    V600,
    V610_1,
    V610_2,
    V611,
    V612_1,
    V612_2,
    V613_1,
    V613_2,
    V613_3,
    V613_4,
    V614_1,
    V614_2,
    V614_3,
    V614_4,
    V615_1,
    V615_2,
    V615_3,
    V616,
    V617,
    V618,
    V620,
    V621,
    V622,
    V623,
    V624,
    V628_1,
    V628_2,
    V628_3,
    V628_4,
    V630,
    V633,
    V634,
    V635_1,
    V635_2,
    V635_3,
    V635_4,
    V636_1,
    V636_2,
    V636_3,
    V637,
    V638_1,
    V638_2,
    V638_3,
    V638_4,
    V639_1,
    V639_2,
    V639_3,
    V640,
    V641,
    V642_1,
    V642_2,
    V643,
    V644_1,
    V644_2,
    V645,
    V646_1,
    V646_2,
    V647,
    V648_1,
    V648_2,
    V649,
    V650,
    V651_1,
    V651_2,
    V652_1,
    V652_2,
    V654_1,
    V654_2,
    V655_1,
    V655_2,
    V656_1,
    V656_2,
    V657_1,
    V657_2,
    V657_3,
    V658_1,
    V658_2,
    V659,
    V660_1,
    V660_2,
    V661,
    V662_1,
    V662_2,
    V663,
    V664,
    V665_1,
    V665_2,
    V666_1,
    V666_2,
    V667_1,
    V667_2,
    V668_1,
    V668_2,
    V669_1,
    V669_2,
    V669_3,
    V669_4,
    V670_1,
    V670_2,
    V670_3,
    V670_4,
    V672_1,
    V672_2,
    V672_3,
    V672_4,
    V672_5,
    V672_6,
    V673_1,
    V673_2,
    V673_3,
    V673_4,
    V673_5,
    V674_1,
    V674_2,
    V674_3,
    V674_4,
    V674_5,
    V675_1,
    V675_2,
    V675_3,
    V675_4,
    V675_5,
    V676_1,
    V676_2,
    V676_3,
    V676_4,
    V676_5,
    V680_1,
    V680_2,
    V682_1,
    V682_2,
    V683,
    V688_1,
    V688_2,
    V689_1,
    V689_2,
    V689_3,
    V690_1,
    V690_2,
    V690_3,
    V691,
    V695,
    V699,
    V700_1,
    V700_2,
    V701,
    V702_1,
    V702_2,
    V703_1,
    V703_2,
    V708,
    V711,
    V715
  )

  override val qualityChecks = Vector(
    Q601,
    Q602,
    Q605_1,
    Q605_2,
    Q606,
    Q609,
    Q618,
    Q607,
    Q608,
    Q610,
    Q611,
    Q612,
    Q613,
    Q614,
    Q615_1,
    Q615_2,
    Q616_1,
    Q616_2,
    Q617,
    Q618,
    Q619,
    Q620,
    Q621,
    Q622,
    Q623,
    Q624,
    Q625,
    Q626,
    Q627,
    Q628,
    Q629,
    Q630,
    Q631,
    Q632,
    Q633,
    Q642_1,
    Q642_2,
    Q643,
    Q644
  )

}
