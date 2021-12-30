package com.bartoszdrozd.mediapp.utils;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class PredictionContract extends Contract {
    public static final String BINARY = "{\r\n"
            + "\t\"functionDebugData\": {\r\n"
            + "\t\t\"@_31\": {\r\n"
            + "\t\t\t\"entryPoint\": null,\r\n"
            + "\t\t\t\"id\": 31,\r\n"
            + "\t\t\t\"parameterSlots\": 0,\r\n"
            + "\t\t\t\"returnSlots\": 0\r\n"
            + "\t\t}\r\n"
            + "\t},\r\n"
            + "\t\"generatedSources\": [],\r\n"
            + "\t\"linkReferences\": {},\r\n"
            + "\t\"object\": \"608060405234801561001057600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610ca4806100606000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c80637b4fd3e31461003b578063d30ec2d51461006d575b600080fd5b610055600480360381019061005091906103f8565b610089565b6040516100649392919061060c565b60405180910390f35b61008760048036038101906100829190610441565b610261565b005b6001818051602081018201805184825260208301602085012081835280955050505050506000915090508060000180546100c290610a2c565b80601f01602080910402602001604051908101604052809291908181526020018280546100ee90610a2c565b801561013b5780601f106101105761010080835404028352916020019161013b565b820191906000526020600020905b81548152906001019060200180831161011e57829003601f168201915b50505050509080600101805461015090610a2c565b80601f016020809104026020016040519081016040528092919081815260200182805461017c90610a2c565b80156101c95780601f1061019e576101008083540402835291602001916101c9565b820191906000526020600020905b8154815290600101906020018083116101ac57829003601f168201915b5050505050908060020180546101de90610a2c565b80601f016020809104026020016040519081016040528092919081815260200182805461020a90610a2c565b80156102575780601f1061022c57610100808354040283529160200191610257565b820191906000526020600020905b81548152906001019060200180831161023a57829003601f168201915b5050505050905083565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146102ef576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102e690610658565b60405180910390fd5b8060018280600001906103029190610678565b6040516103109291906105c3565b9081526020016040518091039020818161032a9190610c36565b9050507f2f31f15379d17e949a3df1d7630a55a19afef378756006dcdb7a23f3780826d1338260405161035e9291906105dc565b60405180910390a150565b600061037c61037784610700565b6106db565b90508281526020810184848401111561039857610397610b54565b5b6103a3848285610973565b509392505050565b600082601f8301126103c0576103bf610b36565b5b81356103d0848260208601610369565b91505092915050565b6000606082840312156103ef576103ee610b40565b5b81905092915050565b60006020828403121561040e5761040d610b63565b5b600082013567ffffffffffffffff81111561042c5761042b610b59565b5b610438848285016103ab565b91505092915050565b60006020828403121561045757610456610b63565b5b600082013567ffffffffffffffff81111561047557610474610b59565b5b610481848285016103d9565b91505092915050565b61049381610832565b82525050565b60006104a5838561075c565b93506104b2838584610973565b6104bb83610b68565b840190509392505050565b60006104d2838561077e565b93506104df838584610973565b82840190509392505050565b60006104f682610751565b610500818561076d565b9350610510818560208601610982565b61051981610b68565b840191505092915050565b6000610531600b8361076d565b915061053c82610bab565b602082019050919050565b60006060830161055a6000840184610789565b858303600087015261056d838284610499565b9250505061057e6020840184610789565b8583036020870152610591838284610499565b925050506105a26040840184610789565b85830360408701526105b5838284610499565b925050508091505092915050565b60006105d08284866104c6565b91508190509392505050565b60006040820190506105f1600083018561048a565b81810360208301526106038184610547565b90509392505050565b6000606082019050818103600083015261062681866104eb565b9050818103602083015261063a81856104eb565b9050818103604083015261064e81846104eb565b9050949350505050565b6000602082019050818103600083015261067181610524565b9050919050565b6000808335600160200384360303811261069557610694610b45565b5b80840192508235915067ffffffffffffffff8211156106b7576106b6610b3b565b5b6020830192506001820236038313156106d3576106d2610b4f565b5b509250929050565b60006106e56106f6565b90506106f18282610a7a565b919050565b6000604051905090565b600067ffffffffffffffff82111561071b5761071a610af8565b5b61072482610b68565b9050602081019050919050565b60008190508160005260206000209050919050565b600082905092915050565b600081519050919050565b600082825260208201905092915050565b600082825260208201905092915050565b600081905092915050565b600080833560016020038436030381126107a6576107a5610b5e565b5b83810192508235915060208301925067ffffffffffffffff8211156107ce576107cd610b31565b5b6001820236038413156107e4576107e3610b4a565b5b509250929050565b601f82111561082d576107fe81610731565b61080784610a1c565b81016020851015610816578190505b61082a61082285610a1c565b83018261086e565b50505b505050565b600061083d82610844565b9050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b5b8181101561088d57610882600082610b93565b60018101905061086f565b5050565b600061089c82610864565b9050919050565b6108ad8383610746565b67ffffffffffffffff8111156108c6576108c5610af8565b5b6108d08254610a2c565b6108db8282856107ec565b6000601f83116001811461090a57600084156108f8578287013590505b6109028582610a5e565b86555061096a565b601f19841661091886610731565b60005b828110156109405784890135825560018201915060208501945060208101905061091b565b8683101561095d5784890135610959601f891682610aab565b8355505b6001600288020188555050505b50505050505050565b82818337600083830152505050565b60005b838110156109a0578082015181840152602081019050610985565b838111156109af576000848401525b50505050565b60008101600083016109c78185610678565b6109d2818386610c26565b5050505060018101602083016109e88185610678565b6109f3818386610c26565b505050506002810160408301610a098185610678565b610a14818386610c26565b505050505050565b60006020601f8301049050919050565b60006002820490506001821680610a4457607f821691505b60208210811415610a5857610a57610ac9565b5b50919050565b6000610a6a8383610aab565b9150826002028217905092915050565b610a8382610b68565b810181811067ffffffffffffffff82111715610aa257610aa1610af8565b5b80604052505050565b6000610abc60001984600802610b86565b1980831691505092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6000819050919050565b600080fd5b600080fd5b600080fd5b600080fd5b600080fd5b600080fd5b600080fd5b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b600082821b905092915050565b600082821c905092915050565b610b9b610c69565b610ba6818484610c44565b505050565b7f4e6f7420616c6c6f776564000000000000000000000000000000000000000000600082015250565b600060088302610c047fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610b79565b610c0e8683610b79565b95508019841693508086168417925050509392505050565b610c318383836108a3565b505050565b610c4082826109b5565b5050565b610c4d83610891565b610c61610c5982610b27565b848454610bd4565b825550505050565b60009056fea26469706673582212203db51630808228079a461d864f4dfcde4308316bf93ac834198d1752fadfcda464736f6c63430008070033\",\r\n"
            + "\t\"opcodes\": \"PUSH1 0x80 PUSH1 0x40 MSTORE CALLVALUE DUP1 ISZERO PUSH2 0x10 JUMPI PUSH1 0x0 DUP1 REVERT JUMPDEST POP CALLER PUSH1 0x0 DUP1 PUSH2 0x100 EXP DUP2 SLOAD DUP2 PUSH20 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF MUL NOT AND SWAP1 DUP4 PUSH20 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF AND MUL OR SWAP1 SSTORE POP PUSH2 0xCA4 DUP1 PUSH2 0x60 PUSH1 0x0 CODECOPY PUSH1 0x0 RETURN INVALID PUSH1 0x80 PUSH1 0x40 MSTORE CALLVALUE DUP1 ISZERO PUSH2 0x10 JUMPI PUSH1 0x0 DUP1 REVERT JUMPDEST POP PUSH1 0x4 CALLDATASIZE LT PUSH2 0x36 JUMPI PUSH1 0x0 CALLDATALOAD PUSH1 0xE0 SHR DUP1 PUSH4 0x7B4FD3E3 EQ PUSH2 0x3B JUMPI DUP1 PUSH4 0xD30EC2D5 EQ PUSH2 0x6D JUMPI JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST PUSH2 0x55 PUSH1 0x4 DUP1 CALLDATASIZE SUB DUP2 ADD SWAP1 PUSH2 0x50 SWAP2 SWAP1 PUSH2 0x3F8 JUMP JUMPDEST PUSH2 0x89 JUMP JUMPDEST PUSH1 0x40 MLOAD PUSH2 0x64 SWAP4 SWAP3 SWAP2 SWAP1 PUSH2 0x60C JUMP JUMPDEST PUSH1 0x40 MLOAD DUP1 SWAP2 SUB SWAP1 RETURN JUMPDEST PUSH2 0x87 PUSH1 0x4 DUP1 CALLDATASIZE SUB DUP2 ADD SWAP1 PUSH2 0x82 SWAP2 SWAP1 PUSH2 0x441 JUMP JUMPDEST PUSH2 0x261 JUMP JUMPDEST STOP JUMPDEST PUSH1 0x1 DUP2 DUP1 MLOAD PUSH1 0x20 DUP2 ADD DUP3 ADD DUP1 MLOAD DUP5 DUP3 MSTORE PUSH1 0x20 DUP4 ADD PUSH1 0x20 DUP6 ADD KECCAK256 DUP2 DUP4 MSTORE DUP1 SWAP6 POP POP POP POP POP POP PUSH1 0x0 SWAP2 POP SWAP1 POP DUP1 PUSH1 0x0 ADD DUP1 SLOAD PUSH2 0xC2 SWAP1 PUSH2 0xA2C JUMP JUMPDEST DUP1 PUSH1 0x1F ADD PUSH1 0x20 DUP1 SWAP2 DIV MUL PUSH1 0x20 ADD PUSH1 0x40 MLOAD SWAP1 DUP2 ADD PUSH1 0x40 MSTORE DUP1 SWAP3 SWAP2 SWAP1 DUP2 DUP2 MSTORE PUSH1 0x20 ADD DUP3 DUP1 SLOAD PUSH2 0xEE SWAP1 PUSH2 0xA2C JUMP JUMPDEST DUP1 ISZERO PUSH2 0x13B JUMPI DUP1 PUSH1 0x1F LT PUSH2 0x110 JUMPI PUSH2 0x100 DUP1 DUP4 SLOAD DIV MUL DUP4 MSTORE SWAP2 PUSH1 0x20 ADD SWAP2 PUSH2 0x13B JUMP JUMPDEST DUP3 ADD SWAP2 SWAP1 PUSH1 0x0 MSTORE PUSH1 0x20 PUSH1 0x0 KECCAK256 SWAP1 JUMPDEST DUP2 SLOAD DUP2 MSTORE SWAP1 PUSH1 0x1 ADD SWAP1 PUSH1 0x20 ADD DUP1 DUP4 GT PUSH2 0x11E JUMPI DUP3 SWAP1 SUB PUSH1 0x1F AND DUP3 ADD SWAP2 JUMPDEST POP POP POP POP POP SWAP1 DUP1 PUSH1 0x1 ADD DUP1 SLOAD PUSH2 0x150 SWAP1 PUSH2 0xA2C JUMP JUMPDEST DUP1 PUSH1 0x1F ADD PUSH1 0x20 DUP1 SWAP2 DIV MUL PUSH1 0x20 ADD PUSH1 0x40 MLOAD SWAP1 DUP2 ADD PUSH1 0x40 MSTORE DUP1 SWAP3 SWAP2 SWAP1 DUP2 DUP2 MSTORE PUSH1 0x20 ADD DUP3 DUP1 SLOAD PUSH2 0x17C SWAP1 PUSH2 0xA2C JUMP JUMPDEST DUP1 ISZERO PUSH2 0x1C9 JUMPI DUP1 PUSH1 0x1F LT PUSH2 0x19E JUMPI PUSH2 0x100 DUP1 DUP4 SLOAD DIV MUL DUP4 MSTORE SWAP2 PUSH1 0x20 ADD SWAP2 PUSH2 0x1C9 JUMP JUMPDEST DUP3 ADD SWAP2 SWAP1 PUSH1 0x0 MSTORE PUSH1 0x20 PUSH1 0x0 KECCAK256 SWAP1 JUMPDEST DUP2 SLOAD DUP2 MSTORE SWAP1 PUSH1 0x1 ADD SWAP1 PUSH1 0x20 ADD DUP1 DUP4 GT PUSH2 0x1AC JUMPI DUP3 SWAP1 SUB PUSH1 0x1F AND DUP3 ADD SWAP2 JUMPDEST POP POP POP POP POP SWAP1 DUP1 PUSH1 0x2 ADD DUP1 SLOAD PUSH2 0x1DE SWAP1 PUSH2 0xA2C JUMP JUMPDEST DUP1 PUSH1 0x1F ADD PUSH1 0x20 DUP1 SWAP2 DIV MUL PUSH1 0x20 ADD PUSH1 0x40 MLOAD SWAP1 DUP2 ADD PUSH1 0x40 MSTORE DUP1 SWAP3 SWAP2 SWAP1 DUP2 DUP2 MSTORE PUSH1 0x20 ADD DUP3 DUP1 SLOAD PUSH2 0x20A SWAP1 PUSH2 0xA2C JUMP JUMPDEST DUP1 ISZERO PUSH2 0x257 JUMPI DUP1 PUSH1 0x1F LT PUSH2 0x22C JUMPI PUSH2 0x100 DUP1 DUP4 SLOAD DIV MUL DUP4 MSTORE SWAP2 PUSH1 0x20 ADD SWAP2 PUSH2 0x257 JUMP JUMPDEST DUP3 ADD SWAP2 SWAP1 PUSH1 0x0 MSTORE PUSH1 0x20 PUSH1 0x0 KECCAK256 SWAP1 JUMPDEST DUP2 SLOAD DUP2 MSTORE SWAP1 PUSH1 0x1 ADD SWAP1 PUSH1 0x20 ADD DUP1 DUP4 GT PUSH2 0x23A JUMPI DUP3 SWAP1 SUB PUSH1 0x1F AND DUP3 ADD SWAP2 JUMPDEST POP POP POP POP POP SWAP1 POP DUP4 JUMP JUMPDEST PUSH1 0x0 DUP1 SLOAD SWAP1 PUSH2 0x100 EXP SWAP1 DIV PUSH20 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF AND PUSH20 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF AND CALLER PUSH20 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF AND EQ PUSH2 0x2EF JUMPI PUSH1 0x40 MLOAD PUSH32 0x8C379A000000000000000000000000000000000000000000000000000000000 DUP2 MSTORE PUSH1 0x4 ADD PUSH2 0x2E6 SWAP1 PUSH2 0x658 JUMP JUMPDEST PUSH1 0x40 MLOAD DUP1 SWAP2 SUB SWAP1 REVERT JUMPDEST DUP1 PUSH1 0x1 DUP3 DUP1 PUSH1 0x0 ADD SWAP1 PUSH2 0x302 SWAP2 SWAP1 PUSH2 0x678 JUMP JUMPDEST PUSH1 0x40 MLOAD PUSH2 0x310 SWAP3 SWAP2 SWAP1 PUSH2 0x5C3 JUMP JUMPDEST SWAP1 DUP2 MSTORE PUSH1 0x20 ADD PUSH1 0x40 MLOAD DUP1 SWAP2 SUB SWAP1 KECCAK256 DUP2 DUP2 PUSH2 0x32A SWAP2 SWAP1 PUSH2 0xC36 JUMP JUMPDEST SWAP1 POP POP PUSH32 0x2F31F15379D17E949A3DF1D7630A55A19AFEF378756006DCDB7A23F3780826D1 CALLER DUP3 PUSH1 0x40 MLOAD PUSH2 0x35E SWAP3 SWAP2 SWAP1 PUSH2 0x5DC JUMP JUMPDEST PUSH1 0x40 MLOAD DUP1 SWAP2 SUB SWAP1 LOG1 POP JUMP JUMPDEST PUSH1 0x0 PUSH2 0x37C PUSH2 0x377 DUP5 PUSH2 0x700 JUMP JUMPDEST PUSH2 0x6DB JUMP JUMPDEST SWAP1 POP DUP3 DUP2 MSTORE PUSH1 0x20 DUP2 ADD DUP5 DUP5 DUP5 ADD GT ISZERO PUSH2 0x398 JUMPI PUSH2 0x397 PUSH2 0xB54 JUMP JUMPDEST JUMPDEST PUSH2 0x3A3 DUP5 DUP3 DUP6 PUSH2 0x973 JUMP JUMPDEST POP SWAP4 SWAP3 POP POP POP JUMP JUMPDEST PUSH1 0x0 DUP3 PUSH1 0x1F DUP4 ADD SLT PUSH2 0x3C0 JUMPI PUSH2 0x3BF PUSH2 0xB36 JUMP JUMPDEST JUMPDEST DUP2 CALLDATALOAD PUSH2 0x3D0 DUP5 DUP3 PUSH1 0x20 DUP7 ADD PUSH2 0x369 JUMP JUMPDEST SWAP2 POP POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH1 0x0 PUSH1 0x60 DUP3 DUP5 SUB SLT ISZERO PUSH2 0x3EF JUMPI PUSH2 0x3EE PUSH2 0xB40 JUMP JUMPDEST JUMPDEST DUP2 SWAP1 POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH1 0x0 PUSH1 0x20 DUP3 DUP5 SUB SLT ISZERO PUSH2 0x40E JUMPI PUSH2 0x40D PUSH2 0xB63 JUMP JUMPDEST JUMPDEST PUSH1 0x0 DUP3 ADD CALLDATALOAD PUSH8 0xFFFFFFFFFFFFFFFF DUP2 GT ISZERO PUSH2 0x42C JUMPI PUSH2 0x42B PUSH2 0xB59 JUMP JUMPDEST JUMPDEST PUSH2 0x438 DUP5 DUP3 DUP6 ADD PUSH2 0x3AB JUMP JUMPDEST SWAP2 POP POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH1 0x0 PUSH1 0x20 DUP3 DUP5 SUB SLT ISZERO PUSH2 0x457 JUMPI PUSH2 0x456 PUSH2 0xB63 JUMP JUMPDEST JUMPDEST PUSH1 0x0 DUP3 ADD CALLDATALOAD PUSH8 0xFFFFFFFFFFFFFFFF DUP2 GT ISZERO PUSH2 0x475 JUMPI PUSH2 0x474 PUSH2 0xB59 JUMP JUMPDEST JUMPDEST PUSH2 0x481 DUP5 DUP3 DUP6 ADD PUSH2 0x3D9 JUMP JUMPDEST SWAP2 POP POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH2 0x493 DUP2 PUSH2 0x832 JUMP JUMPDEST DUP3 MSTORE POP POP JUMP JUMPDEST PUSH1 0x0 PUSH2 0x4A5 DUP4 DUP6 PUSH2 0x75C JUMP JUMPDEST SWAP4 POP PUSH2 0x4B2 DUP4 DUP6 DUP5 PUSH2 0x973 JUMP JUMPDEST PUSH2 0x4BB DUP4 PUSH2 0xB68 JUMP JUMPDEST DUP5 ADD SWAP1 POP SWAP4 SWAP3 POP POP POP JUMP JUMPDEST PUSH1 0x0 PUSH2 0x4D2 DUP4 DUP6 PUSH2 0x77E JUMP JUMPDEST SWAP4 POP PUSH2 0x4DF DUP4 DUP6 DUP5 PUSH2 0x973 JUMP JUMPDEST DUP3 DUP5 ADD SWAP1 POP SWAP4 SWAP3 POP POP POP JUMP JUMPDEST PUSH1 0x0 PUSH2 0x4F6 DUP3 PUSH2 0x751 JUMP JUMPDEST PUSH2 0x500 DUP2 DUP6 PUSH2 0x76D JUMP JUMPDEST SWAP4 POP PUSH2 0x510 DUP2 DUP6 PUSH1 0x20 DUP7 ADD PUSH2 0x982 JUMP JUMPDEST PUSH2 0x519 DUP2 PUSH2 0xB68 JUMP JUMPDEST DUP5 ADD SWAP2 POP POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH1 0x0 PUSH2 0x531 PUSH1 0xB DUP4 PUSH2 0x76D JUMP JUMPDEST SWAP2 POP PUSH2 0x53C DUP3 PUSH2 0xBAB JUMP JUMPDEST PUSH1 0x20 DUP3 ADD SWAP1 POP SWAP2 SWAP1 POP JUMP JUMPDEST PUSH1 0x0 PUSH1 0x60 DUP4 ADD PUSH2 0x55A PUSH1 0x0 DUP5 ADD DUP5 PUSH2 0x789 JUMP JUMPDEST DUP6 DUP4 SUB PUSH1 0x0 DUP8 ADD MSTORE PUSH2 0x56D DUP4 DUP3 DUP5 PUSH2 0x499 JUMP JUMPDEST SWAP3 POP POP POP PUSH2 0x57E PUSH1 0x20 DUP5 ADD DUP5 PUSH2 0x789 JUMP JUMPDEST DUP6 DUP4 SUB PUSH1 0x20 DUP8 ADD MSTORE PUSH2 0x591 DUP4 DUP3 DUP5 PUSH2 0x499 JUMP JUMPDEST SWAP3 POP POP POP PUSH2 0x5A2 PUSH1 0x40 DUP5 ADD DUP5 PUSH2 0x789 JUMP JUMPDEST DUP6 DUP4 SUB PUSH1 0x40 DUP8 ADD MSTORE PUSH2 0x5B5 DUP4 DUP3 DUP5 PUSH2 0x499 JUMP JUMPDEST SWAP3 POP POP POP DUP1 SWAP2 POP POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH1 0x0 PUSH2 0x5D0 DUP3 DUP5 DUP7 PUSH2 0x4C6 JUMP JUMPDEST SWAP2 POP DUP2 SWAP1 POP SWAP4 SWAP3 POP POP POP JUMP JUMPDEST PUSH1 0x0 PUSH1 0x40 DUP3 ADD SWAP1 POP PUSH2 0x5F1 PUSH1 0x0 DUP4 ADD DUP6 PUSH2 0x48A JUMP JUMPDEST DUP2 DUP2 SUB PUSH1 0x20 DUP4 ADD MSTORE PUSH2 0x603 DUP2 DUP5 PUSH2 0x547 JUMP JUMPDEST SWAP1 POP SWAP4 SWAP3 POP POP POP JUMP JUMPDEST PUSH1 0x0 PUSH1 0x60 DUP3 ADD SWAP1 POP DUP2 DUP2 SUB PUSH1 0x0 DUP4 ADD MSTORE PUSH2 0x626 DUP2 DUP7 PUSH2 0x4EB JUMP JUMPDEST SWAP1 POP DUP2 DUP2 SUB PUSH1 0x20 DUP4 ADD MSTORE PUSH2 0x63A DUP2 DUP6 PUSH2 0x4EB JUMP JUMPDEST SWAP1 POP DUP2 DUP2 SUB PUSH1 0x40 DUP4 ADD MSTORE PUSH2 0x64E DUP2 DUP5 PUSH2 0x4EB JUMP JUMPDEST SWAP1 POP SWAP5 SWAP4 POP POP POP POP JUMP JUMPDEST PUSH1 0x0 PUSH1 0x20 DUP3 ADD SWAP1 POP DUP2 DUP2 SUB PUSH1 0x0 DUP4 ADD MSTORE PUSH2 0x671 DUP2 PUSH2 0x524 JUMP JUMPDEST SWAP1 POP SWAP2 SWAP1 POP JUMP JUMPDEST PUSH1 0x0 DUP1 DUP4 CALLDATALOAD PUSH1 0x1 PUSH1 0x20 SUB DUP5 CALLDATASIZE SUB SUB DUP2 SLT PUSH2 0x695 JUMPI PUSH2 0x694 PUSH2 0xB45 JUMP JUMPDEST JUMPDEST DUP1 DUP5 ADD SWAP3 POP DUP3 CALLDATALOAD SWAP2 POP PUSH8 0xFFFFFFFFFFFFFFFF DUP3 GT ISZERO PUSH2 0x6B7 JUMPI PUSH2 0x6B6 PUSH2 0xB3B JUMP JUMPDEST JUMPDEST PUSH1 0x20 DUP4 ADD SWAP3 POP PUSH1 0x1 DUP3 MUL CALLDATASIZE SUB DUP4 SGT ISZERO PUSH2 0x6D3 JUMPI PUSH2 0x6D2 PUSH2 0xB4F JUMP JUMPDEST JUMPDEST POP SWAP3 POP SWAP3 SWAP1 POP JUMP JUMPDEST PUSH1 0x0 PUSH2 0x6E5 PUSH2 0x6F6 JUMP JUMPDEST SWAP1 POP PUSH2 0x6F1 DUP3 DUP3 PUSH2 0xA7A JUMP JUMPDEST SWAP2 SWAP1 POP JUMP JUMPDEST PUSH1 0x0 PUSH1 0x40 MLOAD SWAP1 POP SWAP1 JUMP JUMPDEST PUSH1 0x0 PUSH8 0xFFFFFFFFFFFFFFFF DUP3 GT ISZERO PUSH2 0x71B JUMPI PUSH2 0x71A PUSH2 0xAF8 JUMP JUMPDEST JUMPDEST PUSH2 0x724 DUP3 PUSH2 0xB68 JUMP JUMPDEST SWAP1 POP PUSH1 0x20 DUP2 ADD SWAP1 POP SWAP2 SWAP1 POP JUMP JUMPDEST PUSH1 0x0 DUP2 SWAP1 POP DUP2 PUSH1 0x0 MSTORE PUSH1 0x20 PUSH1 0x0 KECCAK256 SWAP1 POP SWAP2 SWAP1 POP JUMP JUMPDEST PUSH1 0x0 DUP3 SWAP1 POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH1 0x0 DUP2 MLOAD SWAP1 POP SWAP2 SWAP1 POP JUMP JUMPDEST PUSH1 0x0 DUP3 DUP3 MSTORE PUSH1 0x20 DUP3 ADD SWAP1 POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH1 0x0 DUP3 DUP3 MSTORE PUSH1 0x20 DUP3 ADD SWAP1 POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH1 0x0 DUP2 SWAP1 POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH1 0x0 DUP1 DUP4 CALLDATALOAD PUSH1 0x1 PUSH1 0x20 SUB DUP5 CALLDATASIZE SUB SUB DUP2 SLT PUSH2 0x7A6 JUMPI PUSH2 0x7A5 PUSH2 0xB5E JUMP JUMPDEST JUMPDEST DUP4 DUP2 ADD SWAP3 POP DUP3 CALLDATALOAD SWAP2 POP PUSH1 0x20 DUP4 ADD SWAP3 POP PUSH8 0xFFFFFFFFFFFFFFFF DUP3 GT ISZERO PUSH2 0x7CE JUMPI PUSH2 0x7CD PUSH2 0xB31 JUMP JUMPDEST JUMPDEST PUSH1 0x1 DUP3 MUL CALLDATASIZE SUB DUP5 SGT ISZERO PUSH2 0x7E4 JUMPI PUSH2 0x7E3 PUSH2 0xB4A JUMP JUMPDEST JUMPDEST POP SWAP3 POP SWAP3 SWAP1 POP JUMP JUMPDEST PUSH1 0x1F DUP3 GT ISZERO PUSH2 0x82D JUMPI PUSH2 0x7FE DUP2 PUSH2 0x731 JUMP JUMPDEST PUSH2 0x807 DUP5 PUSH2 0xA1C JUMP JUMPDEST DUP2 ADD PUSH1 0x20 DUP6 LT ISZERO PUSH2 0x816 JUMPI DUP2 SWAP1 POP JUMPDEST PUSH2 0x82A PUSH2 0x822 DUP6 PUSH2 0xA1C JUMP JUMPDEST DUP4 ADD DUP3 PUSH2 0x86E JUMP JUMPDEST POP POP JUMPDEST POP POP POP JUMP JUMPDEST PUSH1 0x0 PUSH2 0x83D DUP3 PUSH2 0x844 JUMP JUMPDEST SWAP1 POP SWAP2 SWAP1 POP JUMP JUMPDEST PUSH1 0x0 PUSH20 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF DUP3 AND SWAP1 POP SWAP2 SWAP1 POP JUMP JUMPDEST PUSH1 0x0 DUP2 SWAP1 POP SWAP2 SWAP1 POP JUMP JUMPDEST JUMPDEST DUP2 DUP2 LT ISZERO PUSH2 0x88D JUMPI PUSH2 0x882 PUSH1 0x0 DUP3 PUSH2 0xB93 JUMP JUMPDEST PUSH1 0x1 DUP2 ADD SWAP1 POP PUSH2 0x86F JUMP JUMPDEST POP POP JUMP JUMPDEST PUSH1 0x0 PUSH2 0x89C DUP3 PUSH2 0x864 JUMP JUMPDEST SWAP1 POP SWAP2 SWAP1 POP JUMP JUMPDEST PUSH2 0x8AD DUP4 DUP4 PUSH2 0x746 JUMP JUMPDEST PUSH8 0xFFFFFFFFFFFFFFFF DUP2 GT ISZERO PUSH2 0x8C6 JUMPI PUSH2 0x8C5 PUSH2 0xAF8 JUMP JUMPDEST JUMPDEST PUSH2 0x8D0 DUP3 SLOAD PUSH2 0xA2C JUMP JUMPDEST PUSH2 0x8DB DUP3 DUP3 DUP6 PUSH2 0x7EC JUMP JUMPDEST PUSH1 0x0 PUSH1 0x1F DUP4 GT PUSH1 0x1 DUP2 EQ PUSH2 0x90A JUMPI PUSH1 0x0 DUP5 ISZERO PUSH2 0x8F8 JUMPI DUP3 DUP8 ADD CALLDATALOAD SWAP1 POP JUMPDEST PUSH2 0x902 DUP6 DUP3 PUSH2 0xA5E JUMP JUMPDEST DUP7 SSTORE POP PUSH2 0x96A JUMP JUMPDEST PUSH1 0x1F NOT DUP5 AND PUSH2 0x918 DUP7 PUSH2 0x731 JUMP JUMPDEST PUSH1 0x0 JUMPDEST DUP3 DUP2 LT ISZERO PUSH2 0x940 JUMPI DUP5 DUP10 ADD CALLDATALOAD DUP3 SSTORE PUSH1 0x1 DUP3 ADD SWAP2 POP PUSH1 0x20 DUP6 ADD SWAP5 POP PUSH1 0x20 DUP2 ADD SWAP1 POP PUSH2 0x91B JUMP JUMPDEST DUP7 DUP4 LT ISZERO PUSH2 0x95D JUMPI DUP5 DUP10 ADD CALLDATALOAD PUSH2 0x959 PUSH1 0x1F DUP10 AND DUP3 PUSH2 0xAAB JUMP JUMPDEST DUP4 SSTORE POP JUMPDEST PUSH1 0x1 PUSH1 0x2 DUP9 MUL ADD DUP9 SSTORE POP POP POP JUMPDEST POP POP POP POP POP POP POP JUMP JUMPDEST DUP3 DUP2 DUP4 CALLDATACOPY PUSH1 0x0 DUP4 DUP4 ADD MSTORE POP POP POP JUMP JUMPDEST PUSH1 0x0 JUMPDEST DUP4 DUP2 LT ISZERO PUSH2 0x9A0 JUMPI DUP1 DUP3 ADD MLOAD DUP2 DUP5 ADD MSTORE PUSH1 0x20 DUP2 ADD SWAP1 POP PUSH2 0x985 JUMP JUMPDEST DUP4 DUP2 GT ISZERO PUSH2 0x9AF JUMPI PUSH1 0x0 DUP5 DUP5 ADD MSTORE JUMPDEST POP POP POP POP JUMP JUMPDEST PUSH1 0x0 DUP2 ADD PUSH1 0x0 DUP4 ADD PUSH2 0x9C7 DUP2 DUP6 PUSH2 0x678 JUMP JUMPDEST PUSH2 0x9D2 DUP2 DUP4 DUP7 PUSH2 0xC26 JUMP JUMPDEST POP POP POP POP PUSH1 0x1 DUP2 ADD PUSH1 0x20 DUP4 ADD PUSH2 0x9E8 DUP2 DUP6 PUSH2 0x678 JUMP JUMPDEST PUSH2 0x9F3 DUP2 DUP4 DUP7 PUSH2 0xC26 JUMP JUMPDEST POP POP POP POP PUSH1 0x2 DUP2 ADD PUSH1 0x40 DUP4 ADD PUSH2 0xA09 DUP2 DUP6 PUSH2 0x678 JUMP JUMPDEST PUSH2 0xA14 DUP2 DUP4 DUP7 PUSH2 0xC26 JUMP JUMPDEST POP POP POP POP POP POP JUMP JUMPDEST PUSH1 0x0 PUSH1 0x20 PUSH1 0x1F DUP4 ADD DIV SWAP1 POP SWAP2 SWAP1 POP JUMP JUMPDEST PUSH1 0x0 PUSH1 0x2 DUP3 DIV SWAP1 POP PUSH1 0x1 DUP3 AND DUP1 PUSH2 0xA44 JUMPI PUSH1 0x7F DUP3 AND SWAP2 POP JUMPDEST PUSH1 0x20 DUP3 LT DUP2 EQ ISZERO PUSH2 0xA58 JUMPI PUSH2 0xA57 PUSH2 0xAC9 JUMP JUMPDEST JUMPDEST POP SWAP2 SWAP1 POP JUMP JUMPDEST PUSH1 0x0 PUSH2 0xA6A DUP4 DUP4 PUSH2 0xAAB JUMP JUMPDEST SWAP2 POP DUP3 PUSH1 0x2 MUL DUP3 OR SWAP1 POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH2 0xA83 DUP3 PUSH2 0xB68 JUMP JUMPDEST DUP2 ADD DUP2 DUP2 LT PUSH8 0xFFFFFFFFFFFFFFFF DUP3 GT OR ISZERO PUSH2 0xAA2 JUMPI PUSH2 0xAA1 PUSH2 0xAF8 JUMP JUMPDEST JUMPDEST DUP1 PUSH1 0x40 MSTORE POP POP POP JUMP JUMPDEST PUSH1 0x0 PUSH2 0xABC PUSH1 0x0 NOT DUP5 PUSH1 0x8 MUL PUSH2 0xB86 JUMP JUMPDEST NOT DUP1 DUP4 AND SWAP2 POP POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH32 0x4E487B7100000000000000000000000000000000000000000000000000000000 PUSH1 0x0 MSTORE PUSH1 0x22 PUSH1 0x4 MSTORE PUSH1 0x24 PUSH1 0x0 REVERT JUMPDEST PUSH32 0x4E487B7100000000000000000000000000000000000000000000000000000000 PUSH1 0x0 MSTORE PUSH1 0x41 PUSH1 0x4 MSTORE PUSH1 0x24 PUSH1 0x0 REVERT JUMPDEST PUSH1 0x0 DUP2 SWAP1 POP SWAP2 SWAP1 POP JUMP JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST PUSH1 0x0 PUSH1 0x1F NOT PUSH1 0x1F DUP4 ADD AND SWAP1 POP SWAP2 SWAP1 POP JUMP JUMPDEST PUSH1 0x0 DUP3 DUP3 SHL SWAP1 POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH1 0x0 DUP3 DUP3 SHR SWAP1 POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH2 0xB9B PUSH2 0xC69 JUMP JUMPDEST PUSH2 0xBA6 DUP2 DUP5 DUP5 PUSH2 0xC44 JUMP JUMPDEST POP POP POP JUMP JUMPDEST PUSH32 0x4E6F7420616C6C6F776564000000000000000000000000000000000000000000 PUSH1 0x0 DUP3 ADD MSTORE POP JUMP JUMPDEST PUSH1 0x0 PUSH1 0x8 DUP4 MUL PUSH2 0xC04 PUSH32 0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF DUP3 PUSH2 0xB79 JUMP JUMPDEST PUSH2 0xC0E DUP7 DUP4 PUSH2 0xB79 JUMP JUMPDEST SWAP6 POP DUP1 NOT DUP5 AND SWAP4 POP DUP1 DUP7 AND DUP5 OR SWAP3 POP POP POP SWAP4 SWAP3 POP POP POP JUMP JUMPDEST PUSH2 0xC31 DUP4 DUP4 DUP4 PUSH2 0x8A3 JUMP JUMPDEST POP POP POP JUMP JUMPDEST PUSH2 0xC40 DUP3 DUP3 PUSH2 0x9B5 JUMP JUMPDEST POP POP JUMP JUMPDEST PUSH2 0xC4D DUP4 PUSH2 0x891 JUMP JUMPDEST PUSH2 0xC61 PUSH2 0xC59 DUP3 PUSH2 0xB27 JUMP JUMPDEST DUP5 DUP5 SLOAD PUSH2 0xBD4 JUMP JUMPDEST DUP3 SSTORE POP POP POP POP JUMP JUMPDEST PUSH1 0x0 SWAP1 JUMP INVALID LOG2 PUSH5 0x6970667358 0x22 SLT KECCAK256 RETURNDATASIZE 0xB5 AND ADDRESS DUP1 DUP3 0x28 SMOD SWAP11 CHAINID SAR DUP7 0x4F 0x4D 0xFC 0xDE NUMBER ADDMOD BALANCE PUSH12 0xF93AC834198D1752FADFCDA4 PUSH5 0x736F6C6343 STOP ADDMOD SMOD STOP CALLER \",\r\n"
            + "\t\"sourceMap\": \"74:530:0:-:0;;;341:51;;;;;;;;;;374:10;366:5;;:18;;;;;;;;;;;;;;;;;;74:530;;;;;;\"\r\n"
            + "}";

    public static final String FUNC_PREDICTIONS = "predictions";

    public static final String FUNC_SAVE = "save";

    public static final Event PREDICTIONSAVED_EVENT = new Event("PredictionSaved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Entry>() {}));
    ;

    @Deprecated
    protected PredictionContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PredictionContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PredictionContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PredictionContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<PredictionSavedEventResponse> getPredictionSavedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PREDICTIONSAVED_EVENT, transactionReceipt);
        ArrayList<PredictionSavedEventResponse> responses = new ArrayList<PredictionSavedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PredictionSavedEventResponse typedResponse = new PredictionSavedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.entry = (Entry) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PredictionSavedEventResponse> predictionSavedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PredictionSavedEventResponse>() {
            @Override
            public PredictionSavedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PREDICTIONSAVED_EVENT, log);
                PredictionSavedEventResponse typedResponse = new PredictionSavedEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.entry = (Entry) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Flowable<PredictionSavedEventResponse> predictionSavedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PREDICTIONSAVED_EVENT));
        return predictionSavedEventFlowable(filter);
    }

    public RemoteFunctionCall<Tuple3<String, String, String>> predictions(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PREDICTIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple3<String, String, String>>(function,
                new Callable<Tuple3<String, String, String>>() {
                    @Override
                    public Tuple3<String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> save(Entry entry) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SAVE, 
                Arrays.<Type>asList(entry), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static PredictionContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PredictionContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PredictionContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PredictionContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PredictionContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PredictionContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PredictionContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PredictionContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PredictionContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PredictionContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<PredictionContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PredictionContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PredictionContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PredictionContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PredictionContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PredictionContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class Entry extends DynamicStruct {
        public String userid;

        public String predictionType;

        public String value;

        public Entry(String userid, String predictionType, String value) {
            super(new org.web3j.abi.datatypes.Utf8String(userid),new org.web3j.abi.datatypes.Utf8String(predictionType),new org.web3j.abi.datatypes.Utf8String(value));
            this.userid = userid;
            this.predictionType = predictionType;
            this.value = value;
        }

        public Entry(Utf8String userid, Utf8String predictionType, Utf8String value) {
            super(userid,predictionType,value);
            this.userid = userid.getValue();
            this.predictionType = predictionType.getValue();
            this.value = value.getValue();
        }
    }

    public static class PredictionSavedEventResponse extends BaseEventResponse {
        public String sender;

        public Entry entry;
    }
}
