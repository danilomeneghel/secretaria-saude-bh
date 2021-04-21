
export interface PiwebColunaModel {
    nomeColuna: string;
    nomeAtributoModelo: string;
    default?: boolean;
    classeCustomizada?: (item: any) => string;
}