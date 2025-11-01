// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract KJMToken {
    // 代币固定名称
    string private constant _name = "KJMToken";
    // 代币符号（可自定义，此处用"KJM"）
    string private constant _symbol = "KJM";
    // 小数位数（默认18位）
    uint8 private constant _decimals = 18;
    // 总供应量
    uint256 private _totalSupply;

    // 地址余额映射
    mapping(address => uint256) private _balances;
    // 授权映射（owner => spender => 授权额度）
    mapping(address => mapping(address => uint256)) private _allowances;

    // 转账事件
    event Transfer(address indexed _from, address indexed _to, uint256 _value);
    // 授权事件
    event Approval(address indexed _owner, address indexed _spender, uint256 _value);

    /**
     * @dev 构造函数：初始化初始供应量（分配给部署者）
     * @param initialSupply_ 初始供应量（已考虑18位小数，如1000 * 10**18表示1000个代币）
     */
    constructor(uint256 initialSupply_) {
        _totalSupply = initialSupply_;
        _balances[msg.sender] = _totalSupply;
        emit Transfer(address(0), msg.sender, _totalSupply);
    }

    // ==== IERC20 标准接口实现 ====
    function name() public pure returns (string memory) {
    return "KJMToken";
}

    function symbol() public pure returns (string memory) {
        return _symbol;
    }

    function decimals() public pure returns (uint8) {
        return _decimals;
    }

    function totalSupply() public view returns (uint256) {
        return _totalSupply;
    }

    function balanceOf(address _owner) public view returns (uint256 balance) {
        return _balances[_owner];
    }

    function transfer(address _to, uint256 _value) public returns (bool success) {
        require(_to != address(0), "Transfer to zero address");
        require(_balances[msg.sender] >= _value, "Insufficient balance");

        _balances[msg.sender] -= _value;
        _balances[_to] += _value;
        emit Transfer(msg.sender, _to, _value);
        return true;
    }

    function transferFrom(
        address _from,
        address _to,
        uint256 _value
    ) public returns (bool success) {
        require(_to != address(0), "Transfer to zero address");
        require(_balances[_from] >= _value, "Insufficient balance");
        require(_allowances[_from][msg.sender] >= _value, "Allowance exceeded");

        _balances[_from] -= _value;
        _balances[_to] += _value;
        _allowances[_from][msg.sender] -= _value;
        emit Transfer(_from, _to, _value);
        return true;
    }

    function approve(address _spender, uint256 _value) public returns (bool success) {
        require(_spender != address(0), "Approve to zero address");
        _allowances[msg.sender][_spender] = _value;
        emit Approval(msg.sender, _spender, _value);
        return true;
    }

    function allowance(address _owner, address _spender) public view returns (uint256 remaining) {
        return _allowances[_owner][_spender];
    }

    // ==== 扩展功能：发行（mint）和销毁（burn） ====
    /**
     * @dev 发行新代币（增加总供应量，分配给指定地址）
     * @param _to 接收新代币的地址
     * @param _amount 发行数量（需包含18位小数）
     */
    function mint(address _to, uint256 _amount) public {
        require(_to != address(0), "Mint to zero address");
        require(_amount > 0, "Mint amount must be positive");

        _totalSupply += _amount;
        _balances[_to] += _amount;
        emit Transfer(address(0), _to, _amount); // 从0地址转账表示发行
    }

    /**
     * @dev 销毁代币（减少总供应量，从调用者余额中扣除）
     * @param _amount 销毁数量（需包含18位小数）
     */
    function burn(uint256 _amount) public {
        require(_amount > 0, "Burn amount must be positive");
        require(_balances[msg.sender] >= _amount, "Insufficient balance to burn");

        _balances[msg.sender] -= _amount;
        _totalSupply -= _amount;
        emit Transfer(msg.sender, address(0), _amount); // 转账到0地址表示销毁
    }
}